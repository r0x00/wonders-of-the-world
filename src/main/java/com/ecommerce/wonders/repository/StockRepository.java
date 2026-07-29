package com.ecommerce.wonders.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.wonders.model.Stock;

public interface StockRepository extends JpaRepository<Stock, Long> {
    Page<Stock> getAllByStoreId(Long storeId, Pageable pageable);

    Optional<Stock> findByProductId(Long productId);
}
