package com.ecommerce.wonders.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.wonders.model.Store;

public interface StoreRepository extends JpaRepository<Store, Long> {
    
}
