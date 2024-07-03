package com.opensell.entities.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opensell.entities.Ad;
import com.opensell.entities.ad.AdImage;
import com.opensell.entities.ad.AdShape;
import com.opensell.entities.ad.AdVisibility;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.util.List;
import java.util.Set;

@Builder
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

    Set<String> tags,

    @Positive
    int adTypeId,

    @Max(AdShape.MAX)
    @Min(0)
    int shape,

    @Max(AdVisibility.MAX)
    @Min(0)
    int visibility,

    String adImagesJson
) {
    public static AdCreator fromAd(Ad ad) throws JsonProcessingException {
        return AdCreator.builder()
            .adId(ad.getIdAd())
            .customerId(ad.getCustomer().getIdCustomer())
            .title(ad.getTitle())
            .price(ad.getPrice())
            .address(ad.getAddress())
            .isSold(ad.isSold())
            .description(ad.getDescription())
            .tags(ad.getTagsName())
            .adTypeId(ad.getAdType().getIdAdType())
            .shape(ad.getShape())
            .visibility(ad.getVisibility())
            .adImagesJson(new ObjectMapper().writeValueAsString(ad.getAdImages()))
            .build();
    }
}
