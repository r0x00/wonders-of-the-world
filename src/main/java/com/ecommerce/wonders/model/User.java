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
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User extends BaseEntity {
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
    private String email;

    @Column(
        nullable = false,
        unique = false,
        length = 200
    )
    private String name;


    @OneToMany(
        mappedBy = "user",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<Rating> ratings = new ArrayList<Rating>();

    @OneToMany(
        mappedBy = "user"
    )
    private List<Order> orders = new ArrayList<Order>();


    @OneToMany(
        mappedBy = "user",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<Address> adresses = new ArrayList<Address>();

    @OneToMany(
        mappedBy = "user",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<Payment> payments = new ArrayList<Payment>();

    //rating
    //order
    //address
    //payment
}
