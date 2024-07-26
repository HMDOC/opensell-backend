package com.opensell.model;

import java.time.LocalDateTime;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.opensell.model.customer.VerificationCode;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @Column(unique = true)
    private String username;

    @NotBlank
    @Column(unique = true)
    private String email;

    @NotBlank
    private String pwd;

    @Builder.Default
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private boolean isDeleted = false;

    @Builder.Default
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private boolean isActivated = false;

    @Builder.Default
    @Column(nullable = false, columnDefinition = "DATETIME DEFAULT NOW()")
    private LocalDateTime joinedDate = LocalDateTime.now();

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Ad> ads;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    List<VerificationCode> verificationCodes;

    private String firstName;

    private String lastName;

    private String bio;

    private String iconPath;
}
