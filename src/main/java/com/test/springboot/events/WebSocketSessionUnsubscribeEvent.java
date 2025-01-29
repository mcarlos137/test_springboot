package com.test.springboot.events;

import org.springframework.context.ApplicationEvent;

public class WebSocketSessionUnsubscribeEvent extends ApplicationEvent {
    
    private String operation;

    public WebSocketSessionUnsubscribeEvent(Object source, String operation) {
        super(source);
        this.operation = operation;
    }

    public String getOperation() {
        return operation;
    }

}