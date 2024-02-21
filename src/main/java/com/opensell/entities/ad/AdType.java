package com.opensell.entities.ad;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Data @AllArgsConstructor @NoArgsConstructor
public class AdType {
    @Id private int idAdType;

    @Column(nullable = false, unique = true, length = 60)
    private String name;
}