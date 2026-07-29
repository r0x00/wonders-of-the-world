package com.ecommerce.wonders.model;

import org.hibernate.annotations.DynamicUpdate;

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
@Table(name = "orders")
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    private Long id;

    @ManyToOne(
        fetch = FetchType.LAZY
    )
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @ManyToOne(
        fetch = FetchType.LAZY
    )
    @JoinColumn(name = "product_id", nullable = false, updatable = false)
    private Product product;

    // user
    // product
    
}
