package com.ecommerce.wonders.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ecommerce.wonders.model.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<Address> findByIdAndUserId(Long id, Long userId);

    Page<Address> getAllByUserId(Long userId, Pageable pageable);
}
