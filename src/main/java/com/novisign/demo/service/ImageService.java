package com.novisign.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.novisign.demo.event.producer.ImageKafkaEventProducer;
import com.novisign.demo.model.dto.Image;
import com.novisign.demo.repository.ImageRepository;
import com.novisign.demo.service.validator.ImageValidator;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

/**
 * Dev notes:
 * - also there can be another wrapper for service to separate business logic with sending event operations
 */
@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;
    private final ImageKafkaEventProducer imageKafkaEventProducer;

    @SneakyThrows
    public Image addImage(Image image) {
        new ImageValidator()
            .withEntity(image)
            .validate();

        Image createdImage = imageRepository.createImage(image);
        imageKafkaEventProducer.sendImageCreatedEvent(createdImage);
        return createdImage;
    }

    public boolean deleteImage(final Long id) {
        if (imageRepository.isExists(id)) {
            imageRepository.deleteImage(id);
            imageKafkaEventProducer.sendImageDeletedEvent(id);
            return true;
        }
        return false;
    }

    public List<Image> searchImages(final String keyword, final Integer duration) {
        return imageRepository.searchImages(keyword, duration);
    }

    public Optional<Image> getImageByUrl(String url) {
        return imageRepository.getImageByUrl(url);
    }

}
