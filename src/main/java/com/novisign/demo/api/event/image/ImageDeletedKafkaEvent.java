package com.novisign.demo.api.event.image;

import com.novisign.demo.api.AbstractKafkaEvent;
import com.novisign.demo.api.EntityType;
import com.novisign.demo.api.EventAction;

import lombok.Getter;

@Getter
public class ImageDeletedKafkaEvent extends AbstractKafkaEvent {

    private Long imageId;

    public ImageDeletedKafkaEvent(Long imageId) {
        super(EntityType.IMAGE, EventAction.DELETED);
        this.imageId = imageId;
    }
}
