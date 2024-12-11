package com.novisign.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.novisign.demo.event.producer.ImageKafkaEventProducer;
import com.novisign.demo.model.dto.Image;
import com.novisign.demo.repository.ImageRepository;

import lombok.RequiredArgsConstructor;

/**
 * Dev notes:
 * - there is should be proxied validator with specific rules and custom exceptions, but I didn't have enough time to finish all steps in time
 * - also there can be another wrapper for service to separate business logic with sending event operations
 */
@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;
    private final ImageKafkaEventProducer imageKafkaEventProducer;

    public Image addImage(Image image) {
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

}
