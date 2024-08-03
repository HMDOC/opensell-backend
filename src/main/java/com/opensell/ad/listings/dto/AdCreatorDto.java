package com.opensell.ad.listings.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opensell.model.Ad;
import com.opensell.enums.AdShape;
import com.opensell.enums.AdVisibility;
import jakarta.validation.constraints.*;
import lombok.Builder;
import java.util.Set;

@Builder
public record AdCreatorDto(
    String adId,

    @NotNull
    String customerId,

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

    String adTypeId,

    @Max(AdShape.MAX)
    @Min(0)
    int shape,

    @Max(AdVisibility.MAX)
    @Min(0)
    int visibility,

    String adImagesJson
) {
    public static AdCreatorDto fromAd(Ad ad) throws JsonProcessingException {
        return AdCreatorDto.builder()
            .adId(ad.getId())
            .customerId(ad.getCustomer().getId())
            .title(ad.getTitle())
            .price(ad.getPrice())
            .address(ad.getAddress())
            .isSold(ad.isSold())
            .description(ad.getDescription())
            .tags(ad.getTags())
            .adTypeId(ad.getAdType().getId())
            .shape(ad.getShape())
            .visibility(ad.getVisibility())
            .adImagesJson(new ObjectMapper().writeValueAsString(ad.getImages()))
            .build();
    }
}
