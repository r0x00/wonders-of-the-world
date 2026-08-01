package com.ecommerce.wonders.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.wonders.model.Store;

public interface StoreRepository extends JpaRepository<Store, Long> {
    Optional<Store> findByUserId(Long userId);
}
