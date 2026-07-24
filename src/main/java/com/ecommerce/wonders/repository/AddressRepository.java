package com.ecommerce.wonders.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.wonders.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
    
}
