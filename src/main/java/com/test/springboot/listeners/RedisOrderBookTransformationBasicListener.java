package com.silkrivercapital.springboot.listeners;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.silkrivercapital.springboot.components.RedisHashMapping;
import com.silkrivercapital.springboot.enums.RedisChannelTopic;
import com.silkrivercapital.springboot.pojos.OrderBook;
import com.silkrivercapital.springboot.pojos.OrderBookTransformationBasic;

public class RedisOrderBookTransformationBasicListener implements MessageListener {

    public final static String TOPIC = RedisChannelTopic.ORDER_BOOK_TRANSFORMATION_BASIC.getLabel();

    @Autowired
    private RedisHashMapping<OrderBookTransformationBasic> orderBookTransformationRedisHashMapping;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        System.out.println("pattern ---------- " + new String(pattern, StandardCharsets.UTF_8));
        OrderBook orderBook;
        try {
            orderBook = new OrderBook(message.toString());
        } catch (JsonProcessingException e) {
            System.out.println("Message is not an order book -> " + message.toString());
            return;
        }

        OrderBookTransformationBasic orderBookTransformationBasic = new OrderBookTransformationBasic(orderBook);

        orderBookTransformationRedisHashMapping.write(orderBook.getTime().toString(), new OrderBookTransformationBasic(orderBook));

        File dataFolder = new File(new File(System.getProperty("user.dir")).getParentFile(), "data");
        File orderBookTransformationBasicFolder = new File(dataFolder, "orderBookTransformationBasic");
        if (!orderBookTransformationBasicFolder.exists()) {
            orderBookTransformationBasicFolder.mkdir();
        }
        File exchangeFolder = new File(orderBookTransformationBasicFolder, orderBook.getExchange());
        if (!exchangeFolder.exists()) {
            exchangeFolder.mkdir();
        }
        File pairFolder = new File(exchangeFolder, orderBook.getPair());
        if (!pairFolder.exists()) {
            pairFolder.mkdir();
        }
        File dataFile = new File(pairFolder, orderBook.getTime().toString() + ".json");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dataFile))) {
            writer.write(orderBookTransformationBasic.toJsonString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}