package com.opensell.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdCategory {
    public static final int MAX_LENGTH = 60;

    @Id
    private String id;

    @Size(min = 1, max = MAX_LENGTH)
    @NotBlank
    @Indexed(unique = true, sparse = true)
    private String name;
}