package com.test.springboot.components;

import java.util.concurrent.CountDownLatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.test.springboot.enums.RedisChannelTopic;
import com.test.springboot.enums.RedisStreamKey;
import com.test.springboot.events.OrderBookGlobalSyncEvent;
import com.test.springboot.pojos.OrderBook;

@Component
public class RabbitmqReceiver {

  private CountDownLatch latch = new CountDownLatch(1);

  @Autowired
  private ApplicationEventPublisher eventPublisher;

  @Autowired
  private RedisPubSub redisPubSub;

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
    // Local Event
    eventPublisher.publishEvent(new OrderBookGlobalSyncEvent(this, "public", orderBook));
    // PUB/SUB Redis
    redisPubSub.publish(RedisChannelTopic.ORDER_BOOK_TRANSFORMATION_BASIC, orderBook.toJsonString());
    //
    redisStreamSender.send(RedisStreamKey.ORDER_BOOK, orderBook.toJsonString());
    latch.countDown();
  }

  public CountDownLatch getLatch() {
    return latch;
  }

}