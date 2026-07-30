package com.ecommerce.wonders.model;

import java.time.YearMonth;

import org.hibernate.annotations.DynamicUpdate;

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
@Table(name = "payment")
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor
public class Payment extends BaseEntity {
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    private Long id;
    
    @Column(
        nullable = false,
        unique = false
    )
    private String token;

    @Column(
        nullable = false,
        unique = false,
        length = 4
    )
    private String last4Digits;

    @Column(
        nullable = false,
        unique = false,
        length = 26
    )
    private String cardHolderName;

    @Column(
        nullable = false,
        unique = false
    )
    private YearMonth expirationDate;

    @Column(
        nullable = false,
        unique = false
    )
    private EnumPaymentMethod paymentMethod;


    @ManyToOne(
        fetch = FetchType.LAZY
    )
    @JoinColumn(name = "user_id",  nullable = false, updatable = false)
    private User user;
    
    //user
}
