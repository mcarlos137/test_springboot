package com.silkrivercapital.springboot.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.silkrivercapital.springboot.events.WebSocketSessionSubscribeEvent;
import com.silkrivercapital.springboot.events.WebSocketSessionUnsubscribeEvent;
import com.silkrivercapital.springboot.services.GlobalService;

@Component
public class WebSocketSessionListener {

    @Autowired
    private GlobalService globalService; 
    
    @EventListener
    public void handleSubscribeEvent(WebSocketSessionSubscribeEvent event) {
        globalService.addSession(event.getOperation(), event.getChannel(), event.getWebSocketSession());
    }

    @EventListener
    public void handleUnsubscribeEvent(WebSocketSessionUnsubscribeEvent event) {
        globalService.removeSessions(event.getOperation());
    }

}