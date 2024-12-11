package com.novisign.demo.api;

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
