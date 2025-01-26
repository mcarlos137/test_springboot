package com.silkrivercapital.springboot.controllers.graphql;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.silkrivercapital.springboot.entities.Trade;
import com.silkrivercapital.springboot.enums.TradeSide;
import com.silkrivercapital.springboot.services.TradeService;

@Controller
public class TradeGQLController {

    @Autowired
    private TradeService tradeService;

    @QueryMapping
    public Trade tradeById(@Argument Integer id) {
        return tradeService.findById(id);
    }

    @QueryMapping
    public List<Trade> tradesByOrderId(@Argument String orderId) {
        return tradeService.findAllByOrderId(orderId);
    }

    @MutationMapping
    public Trade createTrade(
            @Argument String exchange,
            @Argument String baseAsset,
            @Argument String quoteAsset,
            @Argument String botId,
            @Argument String strategy,
            @Argument String side,
            @Argument Double amount,
            @Argument Double price,
            @Argument String fee,
            @Argument String orderId,
            @Argument String exchangeId) {
        Trade trade = new Trade(botId, strategy, baseAsset, quoteAsset, orderId, TradeSide.valueOf(side), amount, price, fee, exchangeId, exchange);
        return tradeService.create(trade);
    }
    
}