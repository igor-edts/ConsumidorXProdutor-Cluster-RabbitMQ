package com.example.producer1.service;

import com.example.common.model.Product;
import com.example.common.model.ProductType;
import com.example.producer1.config.RabbitMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
@Slf4j
public class ProductProducerService {
    private final RabbitTemplate rabbitTemplate;
    private final Random random = new Random();

    public ProductProducerService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Scheduled(fixedRate = 1000) // Tenta produzir a cada 1 segundo
    public void produceProduct() {
        ProductType type = random.nextBoolean() ? ProductType.TYPE_1 : ProductType.TYPE_2;
        Product product = new Product(
            UUID.randomUUID().toString(),
            type,
            "producer-1",
            System.currentTimeMillis()
        );

        String queueName = type == ProductType.TYPE_1 ? RabbitMQConfig.QUEUE_TYPE_1 : RabbitMQConfig.QUEUE_TYPE_2;

        try {
            Thread.sleep(type.getProductionTime()); // Simula o tempo de produção
            rabbitTemplate.convertAndSend(queueName, product);
            log.info("Produto produzido: {} do tipo {} em {} ms", 
                    product.getId(), product.getType(), type.getProductionTime());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Erro ao produzir produto", e);
        }
    }
}