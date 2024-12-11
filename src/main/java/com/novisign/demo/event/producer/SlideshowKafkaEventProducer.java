package com.novisign.demo.event.producer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.novisign.demo.api.event.slideshow.SlideshowCreatedKafkaEvent;
import com.novisign.demo.api.event.slideshow.SlideshowDeletedKafkaEvent;
import com.novisign.demo.config.MessageProducer;
import com.novisign.demo.model.dto.Slideshow;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SlideshowKafkaEventProducer {

    @Value("${kafka.topic.slideshow}")
    private String topic;

    private final MessageProducer producer;

    public void sendSlideshowCreatedEvent(Slideshow slideshow) {
        SlideshowCreatedKafkaEvent event = new SlideshowCreatedKafkaEvent(slideshow);
        producer.sendMessage(topic, event);
    }

    public void sendSlideshowDeletedEvent(Long id) {
        SlideshowDeletedKafkaEvent event = new SlideshowDeletedKafkaEvent(id);
        producer.sendMessage(topic, event);
    }
}
