package com.ecommerce.wonders.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.wonders.model.Stock;

public interface StockRepository extends JpaRepository<Stock, Long> {
    
}
