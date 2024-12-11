package com.novisign.demo.event.producer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.novisign.demo.api.event.image.ImageCreatedKafkaEvent;
import com.novisign.demo.api.event.image.ImageDeletedKafkaEvent;
import com.novisign.demo.config.MessageProducer;
import com.novisign.demo.model.dto.Image;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ImageKafkaEventProducer {

    @Value("${kafka.topic.image}")
    private String topic;

    private final MessageProducer producer;

    public void sendImageCreatedEvent(final Image image) {
        final ImageCreatedKafkaEvent event = new ImageCreatedKafkaEvent(image);
        producer.sendMessage(topic, event);
    }

    public void sendImageDeletedEvent(final Long imageId) {
        final ImageDeletedKafkaEvent event = new ImageDeletedKafkaEvent(imageId);
        producer.sendMessage(topic, event);
    }
}
