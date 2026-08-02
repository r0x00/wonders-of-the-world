package com.ecommerce.wonders.workers;

import org.springframework.stereotype.Component;

import com.ecommerce.wonders.dto.PaymentDto.CreateSqsPayment;
import com.ecommerce.wonders.services.OrderPaymentService;

import io.awspring.cloud.sqs.annotation.SqsListener;

@Component
public class SqsCheckPaymentListener {
    private final OrderPaymentService checkPaymentService;

    public SqsCheckPaymentListener(
        OrderPaymentService checkPaymentService
    ) {
        this.checkPaymentService = checkPaymentService;
    }

    @SqsListener(
        value = "check-payment-queue",
        pollTimeoutSeconds = "10"
    )
    public void listenToCheckPaymentQueue(CreateSqsPayment rawJson) {
        System.out.println("Received message from queue check-payment-queue with Payment JSON: " + rawJson);
        this.checkPaymentService.checkPayment(rawJson.paymentId(), rawJson.orderId());
    }

    @SqsListener(
        value = "check-payment-queue-dlq",
        pollTimeoutSeconds = "10"
    )
     public void listenToCheckPaymentQueueDlq(String message) {
        System.out.println("ALERT: Failed message received from queue check-payment-queue-dlq. Message: " + message);
    }
}
