package com.ecommerce.wonders.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.DynamicUpdate;
import com.ecommerce.wonders.enums.EnumUserPermission;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "users")
@DynamicUpdate
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

    @Column(
        nullable = false
    )
    private EnumUserPermission permission = EnumUserPermission.ROLE_CUSTOMER;

    @Column(
        nullable = false,
        length = 100
    )
    @JsonIgnore 
    private String password;

    @OneToOne(
        mappedBy = "user",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    @JoinColumn(name = "store_id", nullable = true)
    private Store store;

    //rating
    //order
    //address
    //payment
}
