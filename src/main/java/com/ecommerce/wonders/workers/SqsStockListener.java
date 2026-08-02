package com.ecommerce.wonders.workers;

import org.springframework.stereotype.Component;

import com.ecommerce.wonders.dto.StockDto.CreateSqsStock;
import com.ecommerce.wonders.services.OrderStockService;

import io.awspring.cloud.sqs.annotation.SqsListener;

@Component
public class SqsStockListener {
    private final OrderStockService checkStockService;

    public SqsStockListener(
        OrderStockService checkStockService
    ) {
        this.checkStockService = checkStockService;
    }

    @SqsListener(
        value = "check-stock-queue",
        pollTimeoutSeconds = "10"
    )
    public void listenToCheckStockQueue(CreateSqsStock rawJson) {
        System.out.println("Received message from queue check-payment-queue with Payment JSON: " + rawJson);
        this.checkStockService.checkStockByOrderId(rawJson.orderId(), rawJson.paymentId());
    }

    @SqsListener(
        value = "check-stock-queue-dlq",
        pollTimeoutSeconds = "10"
    )
    public void listenToCheckStockQueueDlq(String message) {
        System.out.println("ALERT: Failed message received from queue check-stock-queue-dlq. Message: " + message);
    }
    
}
