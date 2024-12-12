package com.novisign.demo.api.event.image;

import com.novisign.demo.api.event.AbstractKafkaEvent;
import com.novisign.demo.api.enums.EntityType;
import com.novisign.demo.api.enums.EventAction;

import lombok.Getter;

@Getter
public class ImageDeletedKafkaEvent extends AbstractKafkaEvent {

    private Long imageId;

    public ImageDeletedKafkaEvent(Long imageId) {
        super(EntityType.IMAGE, EventAction.DELETED);
        this.imageId = imageId;
    }
}
