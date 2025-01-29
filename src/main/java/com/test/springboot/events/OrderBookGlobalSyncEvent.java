package com.test.springboot.events;

import org.springframework.context.ApplicationEvent;

import com.test.springboot.pojos.OrderBook;

public class OrderBookGlobalSyncEvent extends ApplicationEvent {
    
    private String type;
    private OrderBook orderBook;

    public OrderBookGlobalSyncEvent(Object source, String type, OrderBook orderBook) {
        super(source);
        this.type = type;
        this.orderBook = orderBook;
    }

    public String getType() {
        return type;
    }

    public OrderBook getOrderBook() {
        return orderBook;
    }

}