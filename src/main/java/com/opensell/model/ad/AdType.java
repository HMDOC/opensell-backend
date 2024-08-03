package com.opensell.model.ad;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdType {
    public static final int MAX_LENGTH = 60;

    @MongoId
    private String id;

    @Size(min = 1, max = MAX_LENGTH)
    @NotBlank
    @Indexed(unique = true, sparse = true)
    private String name;
}