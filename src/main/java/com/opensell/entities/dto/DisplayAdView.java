package com.opensell.entities.dto;

import com.opensell.entities.Ad;

public record DisplayAdView(String title,
                            String description,
                            Double price,
                            String firstImage,
                            String link,
                            Boolean isSold,
                            Integer visibility,
                            String reference) {

    public DisplayAdView(Ad ad) {
        this(ad.getTitle(),
                ad.getDescription(),
                ad.getPrice(),
                ad.getAdImages().getFirst().getPath(),
                ad.getLink(),
                ad.isSold(),
                ad.getVisibility(),
                ad.getReference());
    }
}