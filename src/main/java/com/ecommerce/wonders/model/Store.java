package com.ecommerce.wonders.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "store")
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


    // name
    // description
    // image 
    // products
}
