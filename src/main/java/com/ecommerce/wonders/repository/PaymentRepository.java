package com.ecommerce.wonders.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.wonders.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByIdAndUserId(Long id, Long userId);

    Page<Payment> getAllByUserId(Long userId, Pageable pageable);
}