package com.silkrivercapital.springboot.components;

import java.util.concurrent.CountDownLatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.silkrivercapital.springboot.enums.RedisChannelTopic;
import com.silkrivercapital.springboot.enums.RedisStreamKey;
import com.silkrivercapital.springboot.events.OrderBookGlobalSyncEvent;
import com.silkrivercapital.springboot.pojos.OrderBook;

@Component
public class RabbitmqReceiver {

  private CountDownLatch latch = new CountDownLatch(1);

  @Autowired
  private ApplicationEventPublisher eventPublisher;

  @Autowired
  private RedisPublisher redisPublisher;

  @Autowired
  private RedisStreamSender redisStreamSender;

  public void receiveOrderBookMessage(String orderBookmessage) {
    OrderBook orderBook;
    try {
      orderBook = new OrderBook(orderBookmessage);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      System.out.println("Message is not an order book -> " + orderBookmessage);
      return;
    }
    eventPublisher.publishEvent(new OrderBookGlobalSyncEvent(this, "public", orderBook));
    redisPublisher.publish(RedisChannelTopic.ORDER_BOOK_TRANSFORMATION_BASIC, orderBook.toJsonString());
    redisStreamSender.send(RedisStreamKey.ORDER_BOOK, orderBook.toJsonString());
    latch.countDown();
  }

  public CountDownLatch getLatch() {
    return latch;
  }

}