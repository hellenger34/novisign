package com.novisign.demo.service;

import org.springframework.stereotype.Service;

import com.novisign.demo.event.producer.ProofOfPlayKafkaEventProducer;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProofOfPlayService {

    private final ProofOfPlayKafkaEventProducer proofOfPlayKafkaEventProducer;

    public void proofOfPlay(final Long slideshowId, final Long imageId) {
        proofOfPlayKafkaEventProducer.sendProofOfPlayEvent(slideshowId, imageId);
    }

}
