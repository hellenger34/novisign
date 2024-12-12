package com.novisign.demo.api.event.slideshow;

import com.novisign.demo.api.event.AbstractKafkaEvent;
import com.novisign.demo.api.enums.EntityType;
import com.novisign.demo.api.enums.EventAction;

import lombok.Getter;

@Getter
public class SlideshowDeletedKafkaEvent extends AbstractKafkaEvent {

    private Long slideshowId;

    public SlideshowDeletedKafkaEvent(Long slideshowId) {
        super(EntityType.SLIDESHOW, EventAction.DELETED);
        this.slideshowId = slideshowId;
    }
}
