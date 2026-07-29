package com.ecommerce.wonders.model;

import java.util.List;

import org.hibernate.annotations.DynamicUpdate;

import com.ecommerce.wonders.enums.EnumRating;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "rating")
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor
public class Rating extends BaseEntity {
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    private Long id;

    @Column(
        nullable = false,
        unique = false
    )
    private EnumRating rating;

    @Column(
        nullable = true,
        unique = false,
        length = 500
    )
    private String comment;

    @Column(
        nullable = true,
        unique = false
    )
    private List<String> images;

    @ManyToOne(
        fetch = FetchType.LAZY
    )
    @JoinColumn(name = "product_id", nullable = false, updatable = false)
    private Product product;

    @ManyToOne(
        fetch = FetchType.LAZY
    )
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;



    // User
    // product
    // rating
    // comment
    // images

    
}
