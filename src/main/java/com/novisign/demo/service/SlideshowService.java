package com.novisign.demo.service;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.novisign.demo.event.producer.SlideshowKafkaEventProducer;
import com.novisign.demo.model.dto.Image;
import com.novisign.demo.model.dto.Slideshow;
import com.novisign.demo.repository.SlideshowRepository;

import lombok.RequiredArgsConstructor;

/**
 *  * - there is should be proxied validator with specific rules and custom exceptions, but I didn't have enough time to finish all steps in time
 *  * - also there can be another wrapper for service to separate business logic with sending event operations
 */
@Service
@RequiredArgsConstructor
public class SlideshowService {

    private final SlideshowRepository slideshowRepository;
    private final SlideshowKafkaEventProducer producer;

    public Slideshow createSlideshow(final List<Image> images) {
        Slideshow createdSlideshow = slideshowRepository.createSlideshow(images);
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

    public List<Image> getSlideshowOrder(final Long id) throws Exception {
        final Slideshow slideshow = slideshowRepository.getSlideshowById(id)
            .orElseThrow(() -> new Exception(String.format("Slideshow not found by id: %s", id)));

        List<Image> images = slideshow.getImages();

        if (images.isEmpty()) {
            throw new Exception(String.format("There is no images related to slideshow with id: %s", id));
        }

        images.sort(Comparator.comparingInt(Image::getDurationSeconds));
        return images;
    }

}
