package com.novisign.demo.service;

import org.springframework.stereotype.Service;

import com.novisign.demo.event.producer.ProofOfPlayKafkaEventProducer;
import com.novisign.demo.repository.ImageRepository;
import com.novisign.demo.repository.SlideshowRepository;
import com.novisign.demo.service.validator.ProofOfPlayValidator;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@Service
@RequiredArgsConstructor
public class ProofOfPlayService {

    private final ImageRepository imageRepository;
    private final SlideshowRepository slideshowRepository;
    private final ProofOfPlayKafkaEventProducer proofOfPlayKafkaEventProducer;

    @SneakyThrows
    public void proofOfPlay(final Long slideshowId, final Long imageId) {
        new ProofOfPlayValidator()
            .withSlideshowId(slideshowId)
            .withImageId(imageId)
            .withSlideshowRepository(slideshowRepository)
            .withImageRepository(imageRepository)
            .validate();

        proofOfPlayKafkaEventProducer.sendProofOfPlayEvent(slideshowId, imageId);
    }
}
