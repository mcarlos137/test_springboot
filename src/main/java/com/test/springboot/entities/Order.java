package com.test.springboot.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;
import org.json.JSONObject;

import com.test.springboot.enums.OrderSide;
import com.test.springboot.enums.OrderStatus;
import com.test.springboot.enums.OrderType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders", indexes = {
        @Index(name = "orders_id", columnList = "id"),
        @Index(name = "orders_strategy", columnList = "strategy"),
        @Index(name = "orders_exchange_exchangeId", columnList = "exchange, exchange_id", unique = true),
        @Index(name = "orders_exchange_creationTimestampDesc", columnList = "exchange, creation_timestamp DESC"),
        @Index(name = "orders_botId_creationTimestampDesc", columnList = "botId, creation_timestamp DESC"),
        @Index(name = "orders_exchange_type", columnList = "exchange, type"),
        @Index(name = "orders_exchange_lastStatus", columnList = "exchange, lastStatus"),
        @Index(name = "orders_exchange_lastStatus_creationTimestampDesc", columnList = "exchange, lastStatus, creation_timestamp DESC")
})
public class Order implements Serializable {

    @Id
    @UuidGenerator
    private UUID id;
    @Column(nullable = false)
    private String botId;
    @Column(nullable = false)
    private String strategy;
    @Column(nullable = false)
    private String baseAsset;
    @Column(nullable = false)
    private String quoteAsset;
    @Column(nullable = false)
    private Long creationTimestamp;
    @Column(nullable = false)
    private OrderType type;
    @Column(nullable = false)
    private Double amount;
    @Column(nullable = false)
    private Double price;
    @Column(nullable = false)
    private OrderStatus lastStatus;
    @Column(nullable = false)
    private Long lastUpdateTimestamp;
    @Column(nullable = false)
    private String exchangeId;
    @Column(nullable = false)
    private OrderSide side;
    @Column(nullable = false)
    private String exchange;

    public Order() {
    }

    public Order(String botId, String strategy, String baseAsset, String quoteAsset, OrderType type, Double amount,
            Double price, String exchangeId, OrderSide side, String exchange) {
        this.botId = botId;
        this.strategy = strategy;
        this.baseAsset = baseAsset;
        this.quoteAsset = quoteAsset;
        this.type = type;
        this.amount = amount;
        this.price = price;
        this.exchangeId = exchangeId;
        this.side = side;
        this.exchange = exchange;
    }

    public UUID getId() {
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

    public Long getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(Long creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public OrderType getType() {
        return type;
    }

    public Double getAmount() {
        return amount;
    }

    public Double getPrice() {
        return price;
    }

    public OrderStatus getLastStatus() {
        return lastStatus;
    }

    public void setLastStatus(OrderStatus lastStatus) {
        this.lastStatus = lastStatus;
    }

    public Long getLastUpdateTimestamp() {
        return lastUpdateTimestamp;
    }

    public void setLastUpdateTimestamp(Long lastUpdateTimestamp) {
        this.lastUpdateTimestamp = lastUpdateTimestamp;
    }

    public String getExchangeId() {
        return exchangeId;
    }

    public OrderSide getSide() {
        return side;
    }

    public String getExchange() {
        return exchange;
    }

    public JSONObject toJson(){
        JSONObject json = new JSONObject();
        json.put("id", this.id);
        json.put("botId", this.botId);
        json.put("strategy", this.strategy);
        json.put("baseAsset", this.baseAsset);
        json.put("quoteAsset", this.quoteAsset);
        json.put("creationTimestamp", new Date(this.creationTimestamp).toString());
        json.put("type", this.type.name());
        json.put("amount", this.amount);
        json.put("price", this.price);
        json.put("lastStatus", this.lastStatus.name());
        json.put("lastUpdateTimestamp", new Date(this.lastUpdateTimestamp).toString());
        json.put("exchangeId", "exchangeId");
        json.put("side", this.side.name());
        json.put("exchange", "exchange");
        return json;
    }

    @Override
    public String toString() {
        return "Order [id=" + id + ", botId=" + botId + ", strategy=" + strategy + ", baseAsset=" + baseAsset
                + ", quoteAsset=" + quoteAsset + ", creationTimestamp=" + creationTimestamp + ", type=" + type
                + ", amount=" + amount + ", price=" + price + ", lastStatus=" + lastStatus + ", lastUpdateTimestamp="
                + lastUpdateTimestamp + ", exchangeId=" + exchangeId + ", side=" + side + ", exchange=" + exchange
                + "]";
    }

}
