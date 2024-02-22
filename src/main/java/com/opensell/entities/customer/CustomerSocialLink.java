package com.opensell.entities.customer;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Olivier Mansuy
 * need to add limitation(5)
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerSocialLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCustomerSocialLink;

    @Column
    private String link;
}
