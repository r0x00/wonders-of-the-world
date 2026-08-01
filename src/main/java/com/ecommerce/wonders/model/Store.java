package com.ecommerce.wonders.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "store")
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor
public class Store extends BaseEntity {
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    private Long id;    

    @Column(
        nullable = false,
        unique = true,
        length = 200
    )
    private String name;

    @Column(
        nullable = true,
        unique = false,
        length = 500
    )
    private String description;

    @Column(
        nullable = true,
        unique = false
    )
    private String image;

    @OneToMany(
        mappedBy = "store",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<Product> products = new ArrayList<Product>();


    @OneToMany(
        mappedBy = "store"
    )
    private List<Stock> stocks = new ArrayList<Stock>();

    @OneToOne(
        fetch = FetchType.LAZY
    )
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;


    // name
    // description
    // image 
    // products
    // user
}
