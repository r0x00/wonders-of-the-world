package com.ecommerce.wonders.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.wonders.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    
}
