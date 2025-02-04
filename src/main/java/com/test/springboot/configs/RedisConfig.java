package com.test.springboot.configs;

import java.time.Duration;
import java.util.Collections;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.data.redis.stream.StreamMessageListenerContainer.StreamMessageListenerContainerOptions;

import com.test.springboot.listeners.RedisOrderBookTransformationBasicListener;
import com.test.springboot.listeners.RedisStreamOrderBookListener;

@Configuration
@EnableCaching
public class RedisConfig {

        // Cache
        @Bean
        public RedisCacheManager redisCacheManager(RedisConnectionFactory connectionFactory) {
                RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                                .entryTtl(Duration.ofSeconds(1))
                                .disableCachingNullValues();
                RedisCacheManager cacheManager = RedisCacheManager.builder(connectionFactory)
                                .cacheDefaults(cacheConfiguration)
                                .transactionAware()
                                .withInitialCacheConfigurations(Collections.singletonMap("predefined",
                                                RedisCacheConfiguration.defaultCacheConfig()
                                                                .disableCachingNullValues()))
                                .build();
                return cacheManager;
        }

        //Template
        @Bean
        StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
                StringRedisTemplate template = new StringRedisTemplate();
                template.setConnectionFactory(redisConnectionFactory);
                return template;
        }

        // Pub/Sub OrderBookTransformationBasic
        @Bean
        RedisOrderBookTransformationBasicListener redisOrderBookTransformationBasicHandler(){
                return new RedisOrderBookTransformationBasicListener();
        }

        @Bean
        MessageListenerAdapter messageListener1(RedisOrderBookTransformationBasicListener redisOrderBookTransformationBasicHandler) {
                return new MessageListenerAdapter(redisOrderBookTransformationBasicHandler);
        }

        @Bean
        ChannelTopic topic1() {
                return new ChannelTopic(RedisOrderBookTransformationBasicListener.TOPIC);
        }

        // Pub/Sub Container
        @Bean
        public RedisMessageListenerContainer redisMessageListenerContainer(
                        RedisConnectionFactory redisConnectionFactory) {
                RedisMessageListenerContainer container = new RedisMessageListenerContainer();
                container.setConnectionFactory(redisConnectionFactory);
                container.addMessageListener(messageListener1(redisOrderBookTransformationBasicHandler()), topic1());
                return container;
        }

        // Stream Listener
        @Bean
        RedisStreamOrderBookListener redisStreamOrderBookListenerHandler(){
                return new RedisStreamOrderBookListener();
        }

        // Stream Container
        @Bean
        public StreamMessageListenerContainer<String, MapRecord<String, String, String>> streamMessageListenerContainer(
                        RedisConnectionFactory redisConnectionFactory) {
                StreamMessageListenerContainerOptions<String, MapRecord<String, String, String>> containerOptions = StreamMessageListenerContainerOptions
                                .builder().pollTimeout(Duration.ofMillis(100)).build();
                StreamMessageListenerContainer<String, MapRecord<String, String, String>> container = StreamMessageListenerContainer
                                .create(redisConnectionFactory, containerOptions);
                container.receive(StreamOffset.fromStart(RedisStreamOrderBookListener.STREAM_KEY), redisStreamOrderBookListenerHandler());
                return container;
        }

}
