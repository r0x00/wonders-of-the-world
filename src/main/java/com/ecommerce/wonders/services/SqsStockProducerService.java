package com.ecommerce.wonders.services;

import org.springframework.stereotype.Service;

import com.ecommerce.wonders.dto.StockDto.CreateSqsStock;

import io.awspring.cloud.sqs.operations.SqsTemplate;

@Service
public class SqsStockProducerService {
    private final SqsTemplate sqsTemplate;

    public SqsStockProducerService(
        SqsTemplate sqsTemplate
    ) {
        this.sqsTemplate = sqsTemplate;
    }

    public void sendMessageToCheckStockQueue(Long orderId, Long paymentId) {
        String queueName = "check-stock-queue";
        CreateSqsStock rawJson = new CreateSqsStock(orderId, paymentId);

        String message = "Payment JSON: %s. sent to queue: %s".formatted(rawJson, queueName);
        System.out.println(message);

        this.sqsTemplate.send(to -> to
            .queue(queueName)
            .payload(rawJson)
        );
    }
}
