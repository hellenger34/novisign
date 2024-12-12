package com.novisign.demo.api.event;

import com.novisign.demo.api.enums.EntityType;
import com.novisign.demo.api.enums.EventAction;

import lombok.Getter;

@Getter
abstract public class AbstractKafkaEvent {

    private EntityType entityType;
    private EventAction eventAction;

    public AbstractKafkaEvent(EntityType entityType, EventAction eventAction) {
        this.entityType = entityType;
        this.eventAction = eventAction;
    }
}
