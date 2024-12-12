package com.novisign.demo.service;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.novisign.demo.event.producer.SlideshowKafkaEventProducer;
import com.novisign.demo.exception.ExceptionDetail;
import com.novisign.demo.exception.ValidationException;
import com.novisign.demo.model.dto.Image;
import com.novisign.demo.model.dto.Slideshow;
import com.novisign.demo.model.entity.ImageDb;
import com.novisign.demo.repository.SlideshowRepository;
import com.novisign.demo.service.validator.ImageValidator;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

/**
 *  * - also there can be another wrapper for service to separate business logic with sending event operations
 */
@Service
@RequiredArgsConstructor
public class SlideshowService {

    private final SlideshowRepository slideshowRepository;
    private final ImageService imageService;
    private final SlideshowKafkaEventProducer producer;

    @SneakyThrows
    public Slideshow createSlideshow(final List<Image> images) {
        List<Image> imagesRelatedToSlideshow = images.stream()
            .map(image -> imageService.getImageByUrl(image.getUrl()).orElseGet(() -> imageService.addImage(image)))
            .toList();

        Slideshow createdSlideshow = slideshowRepository.createSlideshow(imagesRelatedToSlideshow);
        producer.sendSlideshowCreatedEvent(createdSlideshow);
        return createdSlideshow;
    }

    public boolean deleteSlideshow(final Long id) {
        if (slideshowRepository.isExistsById(id)) {
            slideshowRepository.deleteSlideshow(id);
            producer.sendSlideshowDeletedEvent(id);
            return true;
        }
        return false;
    }

    @SneakyThrows
    public List<Image> getSlideshowOrder(final Long id) {
        final Slideshow slideshow = slideshowRepository.getSlideshowById(id)
            .orElseThrow(() -> new ValidationException(ExceptionDetail.SLIDESHOW_NOT_FOUND, id));

        List<Image> images = slideshow.getImages();

        if (images.isEmpty()) {
            throw new ValidationException(ExceptionDetail.THERE_ID_NOT_IMAGES_RELATED_TO_SLIDESHOW, id);
        }

        images.sort(Comparator.comparingInt(Image::getDurationSeconds));
        return images;
    }

}
