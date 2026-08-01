package com.ecommerce.wonders.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.wonders.model.Rating;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    Page<Rating> getAllByProductId(Long productId, Pageable pageable);

    Page<Rating> getAllByUserId(Long userId, Pageable pageable);

    Optional<Rating> findByUserIdAndProductId(Long userId, Long productId);
}
