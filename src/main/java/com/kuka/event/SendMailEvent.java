package com.kuka.event;

import org.springframework.context.ApplicationEvent;

public class SendMailEvent extends ApplicationEvent {

    public SendMailEvent(Object source) {
        super(source);
    }
}
