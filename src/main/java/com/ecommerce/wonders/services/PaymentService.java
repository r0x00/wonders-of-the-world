package com.ecommerce.wonders.services;

import org.springframework.stereotype.Service;

import com.ecommerce.wonders.repository.PaymentRepository;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }
    
}
