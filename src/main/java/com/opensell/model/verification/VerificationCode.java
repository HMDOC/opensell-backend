package com.opensell.model.verification;

import java.util.Date;
import com.opensell.model.Customer;
import jakarta.persistence.*;
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

    @Enumerated(EnumType.STRING)
    private VerificationCodeType type;

    @Column(columnDefinition = "DATETIME DEFAULT NOW()", nullable = false)
    private Date createdAt;

    public enum VerificationCodeType {
        FIRST_SIGN_UP,
        CHANGE_PASSWORD
    }
}