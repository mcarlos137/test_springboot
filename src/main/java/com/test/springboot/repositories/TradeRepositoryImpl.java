package com.silkrivercapital.springboot.repositories;

import java.util.Date;
import java.util.List;

import com.silkrivercapital.springboot.entities.Trade;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Transactional
public class TradeRepositoryImpl implements TradeRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Trade create(Trade trade) {
        Long currentTime = new Date().getTime();
        trade.setTimestamp(currentTime);
        entityManager.persist(trade);
        return trade;
    }

    @Override
    public Trade findById(Integer id) {
        return entityManager.find(Trade.class, id);
    }


    @Override
    public List<Trade> findAllByBotId(String botId) {
        @SuppressWarnings("unchecked")
        List<Trade> orders = entityManager.createQuery("FROM Trade t WHERE t.botId = :botId")
                .setParameter("botId", botId)
                .getResultList();
        return orders;
    }

    @Override
    public List<Trade> findAllByOrderId(String orderId) {
        @SuppressWarnings("unchecked")
        List<Trade> orders = entityManager.createQuery("FROM Trade t WHERE t.orderId = :orderId")
                .setParameter("orderId", orderId)
                .getResultList();
        return orders;
    }

    @Override
    public List<Trade> findAllByBaseAssetAndQuoteAsset(String baseAsset, String quoteAsset) {
        @SuppressWarnings("unchecked")
        List<Trade> orders = entityManager.createQuery("FROM Trade t WHERE t.baseAsset = :baseAsset AND t.quoteAsset = :quoteAsset")
                .setParameter("baseAsset", baseAsset)
                .setParameter("quoteAsset", quoteAsset)
                .getResultList();
        return orders;
    }

}
