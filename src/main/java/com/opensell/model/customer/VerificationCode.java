package com.opensell.model.customer;

import java.time.LocalDateTime;

import com.opensell.enums.VerificationCodeType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
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

    @Builder.Default
    @Column(columnDefinition = "DATETIME DEFAULT NOW()", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}