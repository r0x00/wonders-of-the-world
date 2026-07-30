package com.ecommerce.wonders.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.DynamicUpdate;

import com.ecommerce.wonders.enums.EnumOrderStatus;
import com.ecommerce.wonders.enums.EnumPaymentMethod;

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

    @Column(
        nullable = false,
        unique = false
    )
    private int quantity;

    @Column(
        nullable = false,
        unique = false
    )
    private EnumOrderStatus status = EnumOrderStatus.PENDING_PAYMENT;

    @Column(
        nullable = false,
        unique = false
    ) 
    private LocalDateTime deliveryDate;

    @Column(
        nullable = false,
        unique = false
    )
    private double total;


    // product snapshot data
    @Column(
        nullable = false,
        unique = false
    )
    private double productPrice;

    @Column(
        nullable = false,
        unique = false
    )
    private String productName;

    @Column(
        nullable = true,
        unique = false
    )
    private String productImage;

    @Column(
        nullable = false,
        unique = false
    )
    private String productStoreName;


    // user payment snapshot data
    @Column(
        nullable = false,
        unique = false
    )
    private EnumPaymentMethod userPaymentMethod;

    @Column(
        nullable = false,
        unique = false
    )
    private String userPaymentLast4Digits;


    // user address snapshot data
    @Column(
        nullable = false,
        unique = false
    )
    private String userAddressName;

    @Column(
        nullable = false,
        unique = false
    )
    private String userAddressStreetAddress;

    @Column(
        nullable = false,
        unique = false
    )
    private String userAddressCity;

    @Column(
        nullable = false,
        unique = false
    )
    private String userAddressState;

    @Column(
        nullable = false,
        unique = false
    )
    private String userAddressCountry;

    @Column(
        nullable = false,
        unique = false
    )
    private String userAddressZipCode;


    // user snapshot data
    @Column(
        nullable = false,
        unique = false
    )
    private String userName;

    @Column(
        nullable = false,
        unique = false
    )
    private String userEmail;

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
