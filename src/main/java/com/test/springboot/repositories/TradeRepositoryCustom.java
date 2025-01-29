package com.test.springboot.repositories;

import java.util.List;

import com.test.springboot.entities.Trade;

public interface TradeRepositoryCustom {
    
    public Trade create(Trade order);

    public Trade findById(Integer id);

    public List<Trade> findAllByBotId(String botId);

    public List<Trade> findAllByOrderId(String orderId);

    public List<Trade> findAllByBaseAssetAndQuoteAsset(String baseAsset, String quoteAsset);

}
