package com.test.springboot.repositories;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.test.springboot.entities.Order;
import com.test.springboot.enums.OrderStatus;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Transactional
public class OrderRepositoryImpl implements OrderRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Order create(Order order) {
        Long currentTime = new Date().getTime();
        order.setCreationTimestamp(currentTime);
        order.setLastStatus(OrderStatus.CREATED);
        order.setLastUpdateTimestamp(currentTime);
        entityManager.persist(order);
        return order;
    }

    @Override
    public Order cancel(UUID id) throws EntityNotFoundException {
        Order order = entityManager.find(Order.class, id);
        if(order == null) throw new EntityNotFoundException("Order not found");
        order.setLastStatus(OrderStatus.CANCELED);
        order.setLastUpdateTimestamp(new Date().getTime());
        return entityManager.merge(order);
    }

    @Override
    public Order findById(UUID id) {
        return entityManager.find(Order.class, id);
    }

    @Override
    public List<Order> findAllByBotId(String botId) {
        @SuppressWarnings("unchecked")
        List<Order> orders = entityManager.createQuery("FROM Order o WHERE o.botId = :botId")
                .setParameter("botId", botId)
                .getResultList();
        return orders;
    }

    @Override
    public List<Order> findAllByBaseAssetAndQuoteAsset(String baseAsset, String quoteAsset) {
        @SuppressWarnings("unchecked")
        List<Order> orders = entityManager.createQuery("FROM Order o WHERE o.baseAsset = :baseAsset AND o.quoteAsset = :quoteAsset")
                .setParameter("baseAsset", baseAsset)
                .setParameter("quoteAsset", quoteAsset)
                .getResultList();
        return orders;
    }

}
