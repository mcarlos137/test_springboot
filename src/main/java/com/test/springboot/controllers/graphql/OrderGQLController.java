package com.silkrivercapital.springboot.controllers.graphql;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import com.silkrivercapital.springboot.entities.Order;
import com.silkrivercapital.springboot.entities.Trade;
import com.silkrivercapital.springboot.enums.OrderSide;
import com.silkrivercapital.springboot.enums.OrderType;
import com.silkrivercapital.springboot.services.OrderService;
import com.silkrivercapital.springboot.services.TradeService;

@Controller
public class OrderGQLController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private TradeService tradeService;

    @QueryMapping
    public Order orderById(@Argument String id) {
        return orderService.findById(UUID.fromString(id));
    }

    @QueryMapping
    public List<Order> ordersByBotId(@Argument String botId) {
        return orderService.findAllByBotId(botId);
    }

    @SchemaMapping
    public List<Trade> trades(Order order) {
        return tradeService.findAllByOrderId(order.getExchangeId());
    }

    @MutationMapping
    public Order createOrder(
            @Argument String exchange,
            @Argument String baseAsset,
            @Argument String quoteAsset,
            @Argument String botId,
            @Argument String strategy,
            @Argument String type,
            @Argument String side,
            @Argument Double amount,
            @Argument Double price,
            @Argument String exchangeId) {
        Order order = new Order(botId, strategy, baseAsset, quoteAsset, OrderType.valueOf(type), amount, price,
                exchangeId, OrderSide.valueOf(side), exchange);
        return orderService.create(order);
    }

    @MutationMapping
    public Order cancelOrder(@Argument String id) {
        return orderService.cancel(UUID.fromString(id));
    }

}