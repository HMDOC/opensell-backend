package com.opensell.entities.dto;

import com.opensell.entities.Ad;
import com.opensell.entities.ad.AdShape;
import com.opensell.entities.ad.AdVisibility;
import jakarta.validation.constraints.*;

import java.util.List;

public record AdCreator(
    Integer adId,

    @NotNull
    Integer customerId,

    @NotBlank
    @Size(max = Ad.TITLE_MAX_LENGTH, min = 2)
    String title,

    @PositiveOrZero
    double price,

    @NotBlank
    String address,

    boolean isSold,

    @NotBlank
    @Size(max = Ad.DESCRIPTION_MAX_LENGTH, min = 5)
    String description,

    List<String> tags,

    @Positive
    int adTypeId,

    @Max(AdShape.MAX)
    @Min(0)
    int shape,

    @Max(AdVisibility.MAX)
    @Min(0)
    int visibility,

    String adImagesJson
) {}
