package com.test.springboot.configs;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.test.springboot.components.RabbitmqReceiver;
import com.test.springboot.enums.RabbitmqQueue;

@Configuration
public class RabbitmqConfig {

    public static final String topicExchangeName = "silk_river_capital";

    @Bean
    Queue queue1() {
        Queue queue = new Queue(RabbitmqQueue.ORDER_BOOK_BINANCE_ETH_USDT.getLabel(), false);
        queue.addArgument("x-max-length", 1); 
        return queue;
    }

    @Bean
    Queue queue2() {
        Queue queue = new Queue(RabbitmqQueue.ORDER_BOOK_BINANCE_SOL_USDT.getLabel(), false);
        queue.addArgument("x-max-length", 1); 
        return queue;
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(topicExchangeName);
    }

    @Bean
    Binding binding1(TopicExchange exchange) {
        return BindingBuilder.bind(queue1()).to(exchange).with(RabbitmqQueue.ORDER_BOOK_BINANCE_ETH_USDT.getLabel());
    }

    @Bean
    Binding binding2(TopicExchange exchange) {
        return BindingBuilder.bind(queue2()).to(exchange).with(RabbitmqQueue.ORDER_BOOK_BINANCE_SOL_USDT.getLabel());
    }

    @Bean
    MessageListenerAdapter listenerAdapterOrderBook(RabbitmqReceiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveOrderBookMessage");
    }

    @Bean
    SimpleMessageListenerContainer container1(RabbitmqReceiver receiver, ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(RabbitmqQueue.ORDER_BOOK_BINANCE_ETH_USDT.getLabel(), RabbitmqQueue.ORDER_BOOK_BINANCE_SOL_USDT.getLabel());
        container.setMessageListener((listenerAdapterOrderBook(receiver)));
        return container; 
    }


}