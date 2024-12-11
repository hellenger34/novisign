package com.novisign.demo.api.event.slideshow;

import com.novisign.demo.api.AbstractKafkaEvent;
import com.novisign.demo.api.EntityType;
import com.novisign.demo.api.EventAction;

import lombok.Getter;

@Getter
public class SlideshowDeletedKafkaEvent extends AbstractKafkaEvent {

    private Long slideshowId;

    public SlideshowDeletedKafkaEvent(Long slideshowId) {
        super(EntityType.SLIDESHOW, EventAction.DELETED);
        this.slideshowId = slideshowId;
    }
}
