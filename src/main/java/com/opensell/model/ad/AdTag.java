package com.opensell.model.ad;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdTag {
    public static final int MAX_LENGTH = 20;

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private int id;

    @Size(max = MAX_LENGTH)
    @NotBlank
    @Column(unique = true)
    private String name;
    
    public AdTag(String name) {this.name = name;}
}
