package com.silkrivercapital.springboot.pojos;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class OrderBookTransformationBasic {

    private Long time;
    private String exchange;
    private String baseAsset;
    private String quoteAsset;

    private Double asksLowestPrice;
    private Double asksHighestPrice;
    private Double asksVolume;

    private Double bidsLowestPrice;
    private Double bidsHighestPrice;
    private Double bidsVolume;

    private Double spread;

    public OrderBookTransformationBasic(Long time, String exchange, String baseAsset, String quoteAsset,
            Double asksLowestPrice, Double asksHighestPrice, Double asksVolume, Double bidsLowestPrice,
            Double bidsHighestPrice, Double bidsVolume, Double spread) {
        this.time = time;
        this.exchange = exchange;
        this.baseAsset = baseAsset;
        this.quoteAsset = quoteAsset;
        this.asksLowestPrice = asksLowestPrice;
        this.asksHighestPrice = asksHighestPrice;
        this.asksVolume = asksVolume;
        this.bidsLowestPrice = bidsLowestPrice;
        this.bidsHighestPrice = bidsHighestPrice;
        this.bidsVolume = bidsVolume;
        this.spread = spread;
    }

    public OrderBookTransformationBasic(OrderBook orderBook) {
        ArrayNode asks = (ArrayNode) orderBook.getAsks();
        int i = 0;
        Double asksLowestPrice = null;
        Double asksHighestPrice = null;
        Double asksVolume = 0.0;
        while (i < asks.size()) {
            ArrayNode asksValues = (ArrayNode) asks.get(i);
            Double price = Double.parseDouble(asksValues.get(0).textValue());
            Double amount = Double.parseDouble(asksValues.get(1).textValue());
            if (asksLowestPrice == null) {
                asksLowestPrice = price;
            }
            if (asksHighestPrice == null || price > asksHighestPrice) {
                asksHighestPrice = price;
            }
            asksVolume += amount;
            i++;
        }
        ArrayNode bids = (ArrayNode) orderBook.getBids();
        int j = 0;
        Double bidsLowestPrice = null;
        Double bidsHighestPrice = null;
        Double bidsVolume = 0.0;
        while (j < bids.size()) {
            ArrayNode bidsValues = (ArrayNode) bids.get(j);
            Double price = Double.parseDouble(bidsValues.get(0).textValue());
            Double amount = Double.parseDouble(bidsValues.get(1).textValue());
            if (bidsLowestPrice == null || price < bidsLowestPrice) {
                bidsLowestPrice = price;
            }
            if (bidsHighestPrice == null) {
                bidsHighestPrice = price;
            }
            bidsVolume += amount;
            j++;
        }
        if(asksLowestPrice == null || bidsHighestPrice == null){
            return;
        }
        Double spread = (asksLowestPrice - bidsHighestPrice) / asksLowestPrice * 10000;
        this.time = orderBook.getTime();
        this.exchange = orderBook.getExchange();
        this.baseAsset = orderBook.getBaseAsset();
        this.quoteAsset = orderBook.getQuoteAsset();
        this.asksLowestPrice = asksLowestPrice;
        this.asksHighestPrice = asksHighestPrice;
        this.asksVolume = asksVolume;
        this.bidsLowestPrice = bidsLowestPrice;
        this.bidsHighestPrice = bidsHighestPrice;
        this.bidsVolume = bidsVolume;
        this.spread = spread;
    }

    public Long getTime() {
        return time;
    }

    public String getExchange() {
        return exchange;
    }

    public String getBaseAsset() {
        return baseAsset;
    }

    public String getQuoteAsset() {
        return quoteAsset;
    }

    public Double getAsksLowestPrice() {
        return asksLowestPrice;
    }

    public Double getAsksHighestPrice() {
        return asksHighestPrice;
    }

    public Double getAsksVolume() {
        return asksVolume;
    }

    public Double getBidsLowestPrice() {
        return bidsLowestPrice;
    }

    public Double getBidsHighestPrice() {
        return bidsHighestPrice;
    }

    public Double getBidsVolume() {
        return bidsVolume;
    }

    public Double getSpread() {
        return spread;
    }

    public String toJsonString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

}
