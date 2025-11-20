package com.example.producer1.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String QUEUE_TYPE_1 = "product.type1";
    public static final String QUEUE_TYPE_2 = "product.type2";

    @Bean
    public Queue queueType1() {
        return new Queue(QUEUE_TYPE_1, true);
    }

    @Bean
    public Queue queueType2() {
        return new Queue(QUEUE_TYPE_2, true);
    }
}