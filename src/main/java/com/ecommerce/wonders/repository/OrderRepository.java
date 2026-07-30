package com.ecommerce.wonders.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.wonders.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{
    Page<Order> getAllByUserId(Long userId, Pageable pageable);
}
