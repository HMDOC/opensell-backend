package com.opensell.model;

import java.time.LocalDateTime;
import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    private String id;

    @NotBlank
    @Indexed(unique = true, sparse = true)
    private String username;

    @NotBlank
    @Indexed(unique = true, sparse = true)
    private String email;

    @NotBlank
    private String pwd;

    @Builder.Default
    @NotNull
    private boolean isDeleted = false;

    @Builder.Default
    @NotNull
    private boolean isActivated = false;

    @Builder.Default
    @NotNull
    private LocalDateTime joinedDate = LocalDateTime.now();

    List<VerificationCode> verificationCodes;

    private String firstName;

    private String lastName;

    private String bio;

    private String iconPath;
}
