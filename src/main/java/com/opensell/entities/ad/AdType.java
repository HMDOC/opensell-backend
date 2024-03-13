package com.opensell.entities.ad;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Data @AllArgsConstructor @NoArgsConstructor
public class AdType {
    @Id 
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int idAdType;

    @Column(nullable = false, unique = true, length = 60)
    private String name;

    public AdType(String name) { this.name = name; }
}