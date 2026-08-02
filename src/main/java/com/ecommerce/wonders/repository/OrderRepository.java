package com.ecommerce.wonders.repository;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ecommerce.wonders.enums.EnumOrderStatus;
import com.ecommerce.wonders.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{
    Page<Order> getAllByUserId(Long userId, Pageable pageable);

    @Modifying
    @Query("UPDATE Order o SET o.status = :status where o.deliveryDate = :currentDate")
    void updateStatusForDeliveryDate(LocalDate currentDate, EnumOrderStatus status);
}
