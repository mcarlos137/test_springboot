package com.test.springboot.events;

import org.springframework.context.ApplicationEvent;
import org.springframework.web.socket.WebSocketSession;

public class WebSocketSessionSubscribeEvent extends ApplicationEvent {
    
    private String operation;
    private String channel;
    private WebSocketSession webSocketSession;

    public WebSocketSessionSubscribeEvent(Object source, String operation, String channel, WebSocketSession webSocketSession) {
        super(source);
        this.operation = operation;
        this.channel = channel;
        this.webSocketSession = webSocketSession;
    }

    public String getOperation() {
        return operation;
    }

    public String getChannel() {
        return channel;
    }

    public WebSocketSession getWebSocketSession() {
        return webSocketSession;
    }

}