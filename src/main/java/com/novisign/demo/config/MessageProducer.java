package com.novisign.demo.config;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.novisign.demo.api.AbstractKafkaEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String topic, AbstractKafkaEvent event) {
        String message = null;
        try {
            message = ObjectMapperProvider.getMapper().writeValueAsString(event);
        } catch (JsonProcessingException e) {
            log.error("Something went wrong with parsing event with exception: ", e);
        }

        kafkaTemplate.send(topic, message);
    }

}
