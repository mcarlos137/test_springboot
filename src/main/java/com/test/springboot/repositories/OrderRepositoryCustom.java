package com.test.springboot.repositories;

import java.util.List;
import java.util.UUID;

import com.test.springboot.entities.Order;

public interface OrderRepositoryCustom {
    
    public Order create(Order order);

    public Order cancel(UUID id);

    public Order findById(UUID id);

    public List<Order> findAllByBotId(String botId);

    public List<Order> findAllByBaseAssetAndQuoteAsset(String baseAsset, String quoteAsset);

}
