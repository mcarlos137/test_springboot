package com.test.springboot.components;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import com.test.springboot.events.OrderBookWebsocketSyncEvent;
import com.test.springboot.events.OrderBookGlobalSyncEvent;
import com.test.springboot.services.GlobalService;
import com.test.springboot.services.WebSocketSessionService;

@Component
public class OrderBookListener {

    @Autowired
    private GlobalService globalService;

    @Autowired
    private WebSocketSessionService webSocketSessionService;

    @EventListener
    public void handleGlobalSyncEvent(OrderBookGlobalSyncEvent event) throws IOException {
        globalService.syncOrderBook(event.getType(), event.getOrderBook());
    }

    @EventListener
    public void handleWebsocketSyncEvent(OrderBookWebsocketSyncEvent event) throws IOException {
        for (WebSocketSession session : globalService.getSessions("orderBook", event.getOrderBook().getChannel())) {
            webSocketSessionService.sendMessage(session, event.getOrderBook().toJsonString());
        }
    }

}