package com.opensell.entities.verification;

import java.util.Date;

import com.opensell.entities.Customer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class VerificationCode {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idVerificationCode;    

    @ManyToOne
    @JoinColumn(name = "customer_id")
    Customer customer;

    @Column
    private String code;

    @Column
    private VerificationCodeType type;

    @Column
    @Temporal(TemporalType.TIMESTAMP) // On précise que c'est un timestamp
    private Date created_at;

    @PrePersist // Avant que le code soit enregistré dans la base de données, on lui attribue une date de création
    public void setDate() {
        created_at = new Date(System.currentTimeMillis());
    }
}