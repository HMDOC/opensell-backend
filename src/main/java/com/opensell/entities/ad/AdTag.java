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
public class AdTag {
    public static final int MAX_LENGTH = 20;

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private int idAdTag;

    @Column(nullable = false, unique = true, length = MAX_LENGTH)
    private String name;
    
    public AdTag(String name) {this.name = name;}

    public static boolean isNameValid(String name) {
        return name != null && name.length() > 0 && name.length() <= MAX_LENGTH;
    }
}
