package com.opensell.entities.customer;

import java.util.List;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Olivier Mansuy
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCustomerInfo;

    @Column(nullable = true)
    private String firstName;

    @Column(nullable = true)
    private String lastName;

    @Column(nullable = true, unique = true)
    private String exposedEmail;

    @Column(nullable = true, unique = true)
    private String phoneNumber;

    @Column(nullable = true)
    private String primaryAddress;

    @Column(nullable = true)
    private String bio;

    @Column(nullable = true)
    private String iconPath;

    @OneToMany
    @JoinColumn(name = "customer_info_id", nullable = false)
    private List<CustomerSocialLink> socials;
}