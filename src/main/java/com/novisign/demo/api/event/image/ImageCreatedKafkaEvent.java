package com.novisign.demo.api.event.image;

import com.novisign.demo.api.AbstractKafkaEvent;
import com.novisign.demo.api.EntityType;
import com.novisign.demo.api.EventAction;
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
