package com.opensell.entities.customer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * need to add limitation(5)
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerSocialLink {

    @Id
    private int idCustomerSocialLink;

    @Column
    private String link;
}
