package com.test.springboot.services;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import com.test.springboot.components.Global;
import com.test.springboot.pojos.OrderBook;

@Service
public class GlobalService {

    @Autowired
    private Global global; 
    
    public void syncOrderBook(String type, OrderBook orderBook) {
        global.syncOrderBook(type, orderBook);
    }

    public Set<WebSocketSession> getSessions(String operation, String channel) {
        return global.getSessions(operation, channel);
    }
    
    public void addSession(String source, String channel, WebSocketSession webSocketSession) {
        global.addSession(source, channel, webSocketSession);
    }

    public void removeSessions(String source) {
        global.removeSessions(source);
    }

}
