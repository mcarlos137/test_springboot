package com.test.springboot.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

import com.test.springboot.enums.RedisChannelTopic;

@Component
public class RedisPublisher {

  @Autowired
  private StringRedisTemplate stringRedisTemplate;

  public void publish(RedisChannelTopic channelTopic, Object channelMessage) {
    stringRedisTemplate.convertAndSend(new ChannelTopic(channelTopic.getLabel()).getTopic(), channelMessage);
  }

}