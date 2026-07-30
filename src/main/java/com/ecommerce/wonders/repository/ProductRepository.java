package com.ecommerce.wonders.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.wonders.enums.EnumCategory;
import com.ecommerce.wonders.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> getAllByStoreId(Long storeId, Pageable pageable);

    Page<Product> getAllByCategory(EnumCategory category, Pageable pageable);
}
