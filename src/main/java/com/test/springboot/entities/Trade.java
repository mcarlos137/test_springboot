package com.test.springboot.entities;

import java.io.Serializable;

import com.test.springboot.enums.TradeSide;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Index;
import jakarta.persistence.SequenceGenerator;

@Entity
@Table(name = "trades", indexes = {
        @Index(name = "trades_id", columnList = "id"),
        @Index(name = "trades_strategy", columnList = "strategy"),
        @Index(name = "trades_exchange_exchangeId", columnList = "exchange, exchange_id", unique = true),
        @Index(name = "trades_exchange_timestampDesc", columnList = "exchange, timestamp DESC"),
        @Index(name = "trades_botId_timestampDesc", columnList = "bot_id, timestamp DESC")
})
public class Trade implements Serializable {

    @Id
    @SequenceGenerator(name="trades_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trades_seq")
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
