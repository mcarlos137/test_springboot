package com.test.springboot.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.test.springboot.entities.Trade;
import com.test.springboot.repositories.TradeRepositoryImpl;

@Service
public class TradeService {

    @Autowired
    private TradeRepositoryImpl repository;
    
    @CachePut(
        value = "trades", 
        key = "#trade.id", 
        cacheManager="redisCacheManager"
    )
    public Trade create(Trade trade) {
        return repository.create(trade);
    }

    public Trade findById(Integer id) {
        return repository.findById(id);
    }

    @Cacheable(
        value = "trades", 
        key = "'botId_' + #botId", 
        cacheManager="redisCacheManager"
    )
    public List<Trade> findAllByBotId(String botId) {
        return repository.findAllByBotId(botId);
    }

    @Cacheable(
        value = "trades", 
        key = "'orderId_' + #orderId", 
        cacheManager="redisCacheManager"
    )
    public List<Trade> findAllByOrderId(String orderId) {
        return repository.findAllByOrderId(orderId);
    }

    @Cacheable(
        value = "trades", 
        key = "'baseAsset_quoteAsset_' + #baseAsset + '_' + #quoteAsset", 
        cacheManager="redisCacheManager"
    )
    public List<Trade> findAllByBaseAssetAndQuoteAsset(String baseAsset, String quoteAsset) {
        return repository.findAllByBaseAssetAndQuoteAsset(baseAsset, quoteAsset);
    }
    
}
