package com.ecommerce.wonders.services;

import org.springframework.stereotype.Service;

import com.ecommerce.wonders.dto.PaymentDto.CreateSqsPayment;

import io.awspring.cloud.sqs.operations.SqsTemplate;

@Service
public class SqsPaymentProducerService {
    private final SqsTemplate sqsTemplate;

    public SqsPaymentProducerService(
        SqsTemplate sqsTemplate
    ) {
        this.sqsTemplate = sqsTemplate;
    }

    public void sendMessageToCheckPaymentQueue(Long paymentId, Long orderId) {
        String queueName = "check-payment-queue";
        CreateSqsPayment rawJson = new CreateSqsPayment(paymentId, orderId);

        String message = "Payment JSON: %s. sent to queue: %s".formatted(rawJson, queueName);
        System.out.println(message);

        this.sqsTemplate.send(to -> to
            .queue(queueName)
            .payload(rawJson)
        );
    }
}
