package com.opensell.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.opensell.ad.catalog.dto.AdPreviewDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Builder
//@Table(uniqueConstraints = {
//	@UniqueConstraint(columnNames = {"title", "customer_id"}, name = "title_customer")
//})
@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ad {
    public static final int DESCRIPTION_MAX_LENGTH = 5000;
    public static final int TITLE_MAX_LENGTH = 80;

    @Id
    private String id;

    @Size(max = TITLE_MAX_LENGTH)
    @NotBlank
    private String title;

    @NotNull
    @PositiveOrZero
    private double price;

    @Builder.Default
    @NotNull
    //"DATETIME DEFAULT NOW()"
    private LocalDateTime addedDate = LocalDateTime.now();

    @Builder.Default
    @NotNull
    private boolean sold = false;

    @Builder.Default
    @NotNull
    private boolean deleted = false;

    @NotNull
    private int visibility;

    @NotNull
    private int shape;

    @Size(max = DESCRIPTION_MAX_LENGTH)
    @NotBlank
    private String description;

    @NotBlank
    private String address;

    @DBRef
    private AdCategory adCategory;

    @Builder.Default
    private Set<String> tags = new LinkedHashSet<>();

    @Builder.Default
    private Set<String> images = new LinkedHashSet<>();

    @DBRef
    @NotNull
    private Customer customer;

    @JsonIgnore
    public String getFirstImagePath() {
        return images.stream().findFirst().orElse(null);
    }

    public AdPreviewDto toAdSearchPreview() {
        return new AdPreviewDto(this);
    }
}