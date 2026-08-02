package com.ecommerce.wonders.services;

import java.util.Random;
import org.springframework.stereotype.Service;


@Service
public class OrderPaymentService {
    private final OrderService orderService;
    private final PaymentService paymentService;

    public OrderPaymentService(
        OrderService orderService,
        PaymentService paymentService
    ) {
        this.orderService = orderService;
        this.paymentService = paymentService;
    }

    public void checkPayment(Long paymentId, Long orderId) {
        Long userId = this.orderService.getOrderById(orderId).userId();

        this.paymentService.getPayment(paymentId, userId);

        int randomNumberToSimulatePayment = new Random().nextInt(10);

        if(randomNumberToSimulatePayment <= 5) {
            this.orderService.orderPaymentFailed(orderId);
            
            throw new Error("Payment failed");
        }

        this.orderService.orderPaymentConfirmed(orderId);
    }
}
