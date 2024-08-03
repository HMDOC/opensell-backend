package com.opensell.model.customer;

import java.time.LocalDateTime;

import com.opensell.enums.VerificationCodeType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerificationCode {
    @NotBlank
    private String code;

    private VerificationCodeType type;

    @Builder.Default
    @NotNull
    //"DATETIME DEFAULT NOW()"
    private LocalDateTime createdAt = LocalDateTime.now();
}