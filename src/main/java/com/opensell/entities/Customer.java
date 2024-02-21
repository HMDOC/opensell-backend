package com.opensell.entities;

import com.opensell.entities.customer.CustomerInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCustomer;

    @Column(nullable = false, columnDefinition = "DATETIME DEFAULT NOW()")
    private Date joinedDate;

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

    @Column(nullable = false, unique = true)
    private String link;
    
    @OneToOne
    @JoinColumn(name = "customer_info_id")
    private CustomerInfo customerInfo;

}
