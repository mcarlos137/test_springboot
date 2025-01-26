package com.silkrivercapital.springboot.components;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;
import org.springframework.web.socket.WebSocketSession;

import com.silkrivercapital.springboot.events.OrderBookWebsocketSyncEvent;
import com.silkrivercapital.springboot.pojos.OrderBook;

@ApplicationScope
@Component
public class Global {

    private final Map<String, Map<String,Set<WebSocketSession>>> sessions = new ConcurrentHashMap<>();
    private final Map<String, Map<String, OrderBook>> orderBooks = new ConcurrentHashMap<>();

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    public Map<String, Set<WebSocketSession>> getSessions(String operation) {
        if (!this.sessions.containsKey(operation)) {
            this.sessions.put(operation, new ConcurrentHashMap<>());
        }
        return this.sessions.get(operation);
    }

    public Set<WebSocketSession> getSessions(String operation, String channel) {
        if (!this.sessions.containsKey(operation)) {
            this.sessions.put(operation, new ConcurrentHashMap<>());
        }
        if (!this.sessions.get(operation).containsKey(channel)) {
            this.sessions.get(operation).put(channel, new HashSet<>());
        }
        return this.sessions.get(operation).get(channel);
    }

    public OrderBook getOrderBook(String type, String exchangePair) {
        if (!this.orderBooks.containsKey(type)) {
            this.orderBooks.put(type, null);
        }
        if (this.orderBooks.get(type) == null) {
            this.orderBooks.get(type).put(exchangePair, null);
        }
        return this.orderBooks.get(type).get(exchangePair);
    }

    public void addSession(String operation, String channel, WebSocketSession webSocketSession) {
        this.getSessions(operation, channel).add(webSocketSession);
    }

    public void removeSessions(String operation) {
        this.getSessions(operation).clear();
    }

    public void syncOrderBook(String type, OrderBook orderBook) {
        Map<String, OrderBook> exchangePairOrderBook = new ConcurrentHashMap<>();
        exchangePairOrderBook.put(orderBook.getChannel(), orderBook);
        this.orderBooks.put(type, exchangePairOrderBook);
        eventPublisher.publishEvent(new OrderBookWebsocketSyncEvent(this, this.mergeOrderBooks(orderBook.getChannel())));
    }

    private OrderBook mergeOrderBooks(String exchangePair){
        return this.orderBooks.get("public").get(exchangePair);
    }

}