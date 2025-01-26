package com.silkrivercapital.springboot.listeners;

import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.stream.StreamListener;

import com.silkrivercapital.springboot.enums.RedisStreamKey;

public class RedisStreamOrderBookListener implements StreamListener<String, MapRecord<String, String, String>> {

	public final static String STREAM_KEY = RedisStreamKey.ORDER_BOOK.getLabel();

	@Override
	public void onMessage(MapRecord<String, String, String> message) {
		System.out.println("MessageId: " + message.getId());
		System.out.println("Stream: " + message.getStream());
		System.out.println("Body: " + message.getValue());
	}
    
}