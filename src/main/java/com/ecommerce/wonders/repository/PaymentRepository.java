package com.ecommerce.wonders.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.wonders.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}