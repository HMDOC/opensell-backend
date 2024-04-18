package com.opensell.entities.customer;
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

    @Column(nullable = true)
    public String link1;

    @Column(nullable = true)
    public String link2;

    @Column(nullable = true)
    public String link3;

    @Column(nullable = true)
    public String link4;

    @Column(nullable = true)
    public String link5;
}