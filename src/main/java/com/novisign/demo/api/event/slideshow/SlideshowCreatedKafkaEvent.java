package com.novisign.demo.api.event.slideshow;

import com.novisign.demo.api.event.AbstractKafkaEvent;
import com.novisign.demo.api.enums.EntityType;
import com.novisign.demo.api.enums.EventAction;
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
