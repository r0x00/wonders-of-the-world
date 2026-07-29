package com.ecommerce.wonders.model;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "stock")
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor
public class Stock extends BaseEntity {
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    private Long id;

    @Column(
        nullable = false,
        unique = false
    )
    private int quantity;

    @OneToOne(
        fetch = FetchType.LAZY
    )
    @JoinColumn(name = "product_id", nullable = false, updatable = false)
    private Product product;

    @ManyToOne(
        fetch = FetchType.LAZY
    )
    @JoinColumn(name = "store_id", nullable = false, updatable = false)
    private Store store;

    // product
    // store
    // quantity
}
