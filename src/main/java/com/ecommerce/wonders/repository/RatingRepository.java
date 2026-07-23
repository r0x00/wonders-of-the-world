package com.ecommerce.wonders.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.wonders.model.Rating;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    
}
