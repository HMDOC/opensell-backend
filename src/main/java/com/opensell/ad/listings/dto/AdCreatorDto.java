package com.opensell.ad.listings.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
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

    boolean sold,

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

    Set<String> images
) {
    public static AdCreatorDto fromAd(Ad ad) throws JsonProcessingException {
        return AdCreatorDto.builder()
            .adId(ad.getId())
            .customerId(ad.getCustomer().getId())
            .title(ad.getTitle())
            .price(ad.getPrice())
            .address(ad.getAddress())
            .sold(ad.isSold())
            .description(ad.getDescription())
            .tags(ad.getTags())
            .adTypeId(ad.getAdCategory().getId())
            .shape(ad.getShape())
            .visibility(ad.getVisibility())
            .images(ad.getImages())
            .build();
    }
}
