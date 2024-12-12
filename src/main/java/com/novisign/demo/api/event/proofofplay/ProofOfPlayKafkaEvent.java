package com.novisign.demo.api.event.proofofplay;

import com.novisign.demo.api.event.AbstractKafkaEvent;
import com.novisign.demo.api.enums.EntityType;
import com.novisign.demo.api.enums.EventAction;

import lombok.Getter;

@Getter
public class ProofOfPlayKafkaEvent extends AbstractKafkaEvent {

    private Long slideshowId;
    private Long imageId;

    public ProofOfPlayKafkaEvent(Long slideshowId, Long imageId) {
        super(EntityType.PROOF_OF_PLAY, EventAction.CREATED);
        this.slideshowId = slideshowId;
        this.imageId = imageId;
    }
}
