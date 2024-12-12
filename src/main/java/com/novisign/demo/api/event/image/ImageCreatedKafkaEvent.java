package com.novisign.demo.api.event.image;

import com.novisign.demo.api.event.AbstractKafkaEvent;
import com.novisign.demo.api.enums.EntityType;
import com.novisign.demo.api.enums.EventAction;
import com.novisign.demo.model.dto.Image;

import lombok.Getter;

@Getter
public class ImageCreatedKafkaEvent extends AbstractKafkaEvent {

    private Image image;

    public ImageCreatedKafkaEvent(Image image) {
        super(EntityType.IMAGE, EventAction.CREATED);
        this.image = image;
    }
}
