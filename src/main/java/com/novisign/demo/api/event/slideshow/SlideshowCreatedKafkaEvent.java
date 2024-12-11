package com.novisign.demo.api.event.slideshow;

import com.novisign.demo.api.AbstractKafkaEvent;
import com.novisign.demo.api.EntityType;
import com.novisign.demo.api.EventAction;
import com.novisign.demo.model.dto.Slideshow;

import lombok.Getter;

@Getter
public class SlideshowCreatedKafkaEvent extends AbstractKafkaEvent {

    private Slideshow slideshow;

    public SlideshowCreatedKafkaEvent(Slideshow slideshow) {
        super(EntityType.SLIDESHOW, EventAction.CREATED);
        this.slideshow = slideshow;
    }
}
