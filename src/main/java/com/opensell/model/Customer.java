package com.opensell.model;

import java.sql.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.opensell.model.customer.CustomerInfo;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
    private int id;

    @NotBlank
    @Column(unique = true)
    private String username;

    @NotBlank
    @Column(unique = true)
    private String personalEmail;

    @NotBlank
    private String pwd;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isDeleted;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isVerified;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isActivated;

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
