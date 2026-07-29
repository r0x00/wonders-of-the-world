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
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "address")
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor
public class Address extends BaseEntity {
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    private Long id;
    
    @Column(
        nullable = false,
        unique = false,
        length = 300
    )
    private String streetAddress;

    @Column(
        nullable = false,
        unique = false,
        length = 100
    )
    private String city;

    @Column(
        nullable = false,
        unique = false,
        length = 60
    )
    private String state;

    @Column(
        nullable = false,
        unique = false,
        length = 60
    )
    private String country;

    @Column(
        nullable = false,
        unique = false,
        length = 15
    )
    private String zipCode;


    @ManyToOne(
        fetch = FetchType.LAZY
    )
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;
    
}
