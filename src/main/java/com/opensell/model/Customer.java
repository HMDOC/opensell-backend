package com.opensell.model;

import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.opensell.model.customer.CustomerInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCustomer;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String personalEmail;

    @Column(nullable = false)
    private String pwd;

    /**
     * "@ColumnDefault("0")"
     */
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isDeleted;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isVerified;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isActivated;

    @Column(nullable = false, unique = true, length=12)
    private String link;

    @OneToOne
    @JoinColumn(name = "customer_info_id")
    private CustomerInfo customerInfo;

    @Column(nullable = false, columnDefinition = "DATETIME DEFAULT NOW()")
    private Date joinedDate;

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "customer")
    private List<Ad> ads;
}
