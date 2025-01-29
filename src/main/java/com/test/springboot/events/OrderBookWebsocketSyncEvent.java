package com.test.springboot.events;

import org.springframework.context.ApplicationEvent;

import com.test.springboot.pojos.OrderBook;

public class OrderBookWebsocketSyncEvent extends ApplicationEvent {
    
    private OrderBook orderBook;

    public OrderBookWebsocketSyncEvent(Object source, OrderBook orderBook) {
        super(source);
        this.orderBook = orderBook;
    }

    public OrderBook getOrderBook() {
        return orderBook;
    }

}