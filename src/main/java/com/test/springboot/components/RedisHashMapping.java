package com.test.springboot.components;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.data.redis.hash.ObjectHashMapper;
import org.springframework.stereotype.Component;

@Component
public class RedisHashMapping<T> {

  private HashMapper<Object, byte[], byte[]> hashMapper = new ObjectHashMapper();

  @Autowired
  private StringRedisTemplate stringRedisTemplate;

  public void write(String key, T t) {
    stringRedisTemplate.execute((RedisCallback<Void>) connection -> {
      Map<byte[], byte[]> mappedHash = hashMapper.toHash(t);
      connection.hashCommands().hMSet(key.getBytes(), mappedHash);
      return null;
    });
  }

  @SuppressWarnings("unchecked")
  public T load(String key) {
    Map<byte[], byte[]> loadedHash = stringRedisTemplate.getConnectionFactory()
        .getConnection().hashCommands().hGetAll(key.getBytes());
    return (T) hashMapper.fromHash(loadedHash);
  }

}