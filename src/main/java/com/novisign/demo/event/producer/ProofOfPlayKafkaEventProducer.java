package com.novisign.demo.event.producer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.novisign.demo.api.event.proofofplay.ProofOfPlayKafkaEvent;
import com.novisign.demo.config.MessageProducer;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProofOfPlayKafkaEventProducer {

    @Value("${kafka.topic.proofofplay}")
    private String topic;

    private final MessageProducer producer;

    public void sendProofOfPlayEvent(Long slideshowId, Long imageId) {
        ProofOfPlayKafkaEvent event = new ProofOfPlayKafkaEvent(slideshowId, imageId);
        producer.sendMessage(topic, event);
    }

}
