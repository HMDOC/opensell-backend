package com.opensell.model.ad;

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
    public static final int MAX_LENGTH = 60;

    @Id 
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int idAdType;

    @Column(nullable = false, unique = true, length = MAX_LENGTH)
    private String name;

    public AdType(String name) { this.name = name; }

    public static boolean isNameValid(String name) {
        return name != null && name.length() > 0 && name.length() <= MAX_LENGTH;
    }
}