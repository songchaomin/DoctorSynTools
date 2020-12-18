package com.kuka.event;

import org.springframework.context.ApplicationEvent;


public class SynInventoryEvent extends ApplicationEvent {
    public SynInventoryEvent(Object source) {
        super(source);
    }
}
