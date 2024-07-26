package com.opensell.model.verification;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
    private int id;

    @NotBlank
    private String code;

    @Enumerated(EnumType.STRING)
    private VerificationCodeType type;

    @Column(columnDefinition = "DATETIME DEFAULT NOW()", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public enum VerificationCodeType {
        FIRST_SIGN_UP,
        CHANGE_PASSWORD
    }
}