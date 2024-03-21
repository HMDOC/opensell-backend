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
@Table(name = "social_link")
public class CustomerSocials {

   @Id
   @OneToOne
   @JoinColumn(name = "customer_info_id")
   public CustomerInfo customerInfo;

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
