package com.test.springboot.controllers.rest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.connection.stream.StreamReadOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.springboot.enums.RedisStreamKey;

@RestController
@RequestMapping("/orderBooks")
public class OrderBookRESTController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping(path = "/getRedisStream", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> getRedisStream(@RequestParam("count") int count) {
        @SuppressWarnings("unchecked")
        List<ObjectRecord<String, String>> records = stringRedisTemplate
                .opsForStream()
                .read(String.class, StreamReadOptions.empty().count(count), StreamOffset.fromStart(RedisStreamKey.ORDER_BOOK.getLabel()));
        List<String> response = new ArrayList<>();
        Iterator<ObjectRecord<String, String>> recordsIterator = records.iterator();
        while (recordsIterator.hasNext()) {
            String value = recordsIterator.next().getValue();
            response.add(value);
        }
        return new ResponseEntity<>(response.toString(), HttpStatus.OK);
    }

}