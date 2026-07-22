package com.ecommerce.wonders.model;

import java.util.ArrayList;
import java.util.List;

import com.ecommerce.wonders.enums.EnumCategory;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="product")
@Getter
@Setter
@NoArgsConstructor
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    private Long id;


    @Column(
        nullable = false,
        unique = false,
        length = 100
    )
    private String name;

    @Column(
        nullable = false,
        unique = false
    )
    private double price;

    @Column(
        nullable = false,
        unique = false
    )
    private EnumCategory category;

    @Column(
        nullable = false,
        unique = false,
        length = 400
    )
    private String description;

    @Column(
        nullable = false,
        unique = false,
        length = 400
    )
    private String details;

    @Column(
        nullable = true,
        unique = false
    )
    private String image;

    @OneToMany(
        mappedBy = "product",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<Rating> ratings = new ArrayList<Rating>();

    @OneToOne(
        mappedBy = "product",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    @JoinColumn(name = "stock_id", nullable = false)
    private Stock stock;

    @ManyToOne(
        fetch = FetchType.LAZY
    )
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;


    // name
    // price
    // category
    // description 
    // details

    // spefications
    // image

    // rating
    // stock
    // store 
}
