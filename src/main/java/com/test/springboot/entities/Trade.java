package com.silkrivercapital.springboot.entities;

import java.io.Serializable;

import com.silkrivercapital.springboot.enums.TradeSide;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Index;

@Entity
@Table(name = "trades", indexes = {
        @Index(columnList = "id"),
        @Index(columnList = "strategy"),
        @Index(name = "exchange_exchangeId", columnList = "exchange, exchangeId", unique = true),
        @Index(name = "exchange_timestampDesc", columnList = "exchange, timestamp DESC"),
        @Index(name = "botId_timestampDesc", columnList = "botId, timestamp DESC")
})
public class Trade implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;
    @Column(nullable = false)
    private String botId;
    @Column(nullable = false)
    private String strategy;
    @Column(nullable = false)
    private String baseAsset;
    @Column(nullable = false)
    private String quoteAsset;
    @Column(nullable = false)
    private Long timestamp;
    @Column(nullable = false)
    private String orderId;
    @Column(nullable = false)
    private TradeSide side;
    @Column(nullable = false)
    private Double amount;
    @Column(nullable = false)
    private Double price;
    @Column(nullable = false)
    private String fee;
    @Column(nullable = false)
    private String exchangeId;
    @Column(nullable = false)
    private String exchange;
    
    public Trade() {
    }

    public Trade(String botId, String strategy, String baseAsset, String quoteAsset,
            String orderId, TradeSide side, Double amount, Double price, String fee, String exchangeId,
            String exchange) {
        this.botId = botId;
        this.strategy = strategy;
        this.baseAsset = baseAsset;
        this.quoteAsset = quoteAsset;
        this.orderId = orderId;
        this.side = side;
        this.amount = amount;
        this.price = price;
        this.fee = fee;
        this.exchangeId = exchangeId;
        this.exchange = exchange;
    }

    public Integer getId() {
        return id;
    }

    public String getBotId() {
        return botId;
    }

    public String getStrategy() {
        return strategy;
    }

    public String getBaseAsset() {
        return baseAsset;
    }

    public String getQuoteAsset() {
        return quoteAsset;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getOrderId() {
        return orderId;
    }

    public TradeSide getSide() {
        return side;
    }

    public Double getAmount() {
        return amount;
    }

    public Double getPrice() {
        return price;
    }

    public String getFee() {
        return fee;
    }

    public String getExchangeId() {
        return exchangeId;
    }

    public String getExchange() {
        return exchange;
    }

}
