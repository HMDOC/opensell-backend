package com.opensell.model.customer;

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
    private int id;

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String exposedEmail;

    @Column(unique = true)
    private String phoneNumber;

    private String bio;

    private String iconPath;
}