package com.example.consumer.service;

import com.example.common.model.Product;
import com.example.common.model.ProductType;
import com.example.consumer.config.RabbitMQConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Consumer_service {

    private static final Logger log = LoggerFactory.getLogger(Consumer_service.class);
    private final String consumerId;

    public Consumer_service(@Value("${consumer.id:consumer-1}") String consumerId) {
        this.consumerId = consumerId;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_TYPE_1)
    public void consumeType1(Product product) throws InterruptedException {
        consume(product);
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_TYPE_2)
    public void consumeType2(Product product) throws InterruptedException {
        consume(product);
    }

    private void consume(Product product) throws InterruptedException {
        ProductType type = product.getType();
        long consumptionTime = type != null ? type.getConsumptionTime() : 0L;

        if (consumptionTime > 0) {
            Thread.sleep(consumptionTime);
        }

        log.info("[{}] Produto consumido: {} do tipo {} em {} ms", consumerId, product.getId(), type, consumptionTime);
    }
}
