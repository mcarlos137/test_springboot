package com.test.springboot.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.test.springboot.entities.Order;
import com.test.springboot.repositories.OrderRepositoryImpl;

@Service
public class OrderService {

    @Autowired
    private OrderRepositoryImpl repository;

    @CachePut(
        value = "orders", 
        key = "#order.id", 
        cacheManager="redisCacheManager"
    )
    public Order create(Order order) {
        return repository.create(order);
    }

    @CachePut(
        value = "orders", 
        key = "#id", 
        cacheManager="redisCacheManager"
    )
    public Order cancel(UUID id) {
        return repository.cancel(id);
    }

    public Order findById(UUID id) {
        return repository.findById(id);
    }

    @Cacheable(
        value = "orders", 
        key = "'botId_' + #botId", 
        cacheManager="redisCacheManager"
    )
    public List<Order> findAllByBotId(String botId) {
        return repository.findAllByBotId(botId);
    }

    @Cacheable(
        value = "orders", 
        key = "'baseAsset_quoteAsset_' + #baseAsset + '_' + #quoteAsset", 
        cacheManager="redisCacheManager"
    )
    public List<Order> findAllByBaseAssetAndQuoteAsset(String baseAsset, String quoteAsset) {
        return repository.findAllByBaseAssetAndQuoteAsset(baseAsset, quoteAsset);
    }
    
}
