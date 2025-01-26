package com.silkrivercapital.springboot.services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.silkrivercapital.springboot.events.WebSocketSessionSubscribeEvent;
import com.silkrivercapital.springboot.events.WebSocketSessionUnsubscribeEvent;

@Service
public class WebSocketSessionService {
    
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    public void subscribe(String operation, String channel, WebSocketSession webSocketSession) {
        eventPublisher.publishEvent(new WebSocketSessionSubscribeEvent(this, operation, channel, webSocketSession));
    }

    public void unsubscribe(String operation) {
        eventPublisher.publishEvent(new WebSocketSessionUnsubscribeEvent(this, operation));
    }

    public void sendMessage(WebSocketSession webSocketSession, String message) throws IOException {
        webSocketSession.sendMessage(new TextMessage(message));
    }

}