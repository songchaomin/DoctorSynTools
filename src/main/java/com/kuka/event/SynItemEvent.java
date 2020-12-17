package com.kuka.event;

import org.springframework.context.ApplicationEvent;


public class SynItemEvent extends ApplicationEvent {
    public SynItemEvent(Object source) {
        super(source);
    }
}
