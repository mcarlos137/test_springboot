package com.test.springboot.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.test.springboot.enums.RedisStreamKey;

@Component
public class RedisStreamSender {

  @Autowired
  private StringRedisTemplate stringRedisTemplate;

  public void send(RedisStreamKey streamKey, Object streamMap) {
    ObjectRecord<String, String> record = StreamRecords.newRecord()
        .in(streamKey.getLabel())
        .ofObject(streamMap.toString());
    stringRedisTemplate.opsForStream().add(record);
  }

}