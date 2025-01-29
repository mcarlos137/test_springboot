package com.test.springboot.pojos;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class OrderBook {

    private Long time;
    private String exchange;
    private String baseAsset;
    private String quoteAsset;
    private JsonNode asks;
    private JsonNode bids;

    public OrderBook(String jsonString) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode data = mapper.readTree(jsonString);
        if(data.has("data")){
            data = data.get("data");
        } 
        this.time = data.get("time").longValue();
        this.exchange = data.get("exchange").textValue();
        this.baseAsset = data.get("baseAsset").textValue();
        this.quoteAsset = data.get("quoteAsset").textValue();
        this.asks = data.get("asks");
        this.bids = data.get("bids");
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

    public String getPair() {
        return this.baseAsset + "-" + this.quoteAsset;
    }

    public JsonNode getAsks() {
        return asks;
    }

    public JsonNode getBids() {
        return bids;
    }

    public String getChannel() {
        return this.exchange + "." + getPair();
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
