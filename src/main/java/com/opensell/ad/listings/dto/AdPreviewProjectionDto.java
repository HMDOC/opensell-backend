package com.opensell.ad.listings.dto;

import org.springframework.beans.factory.annotation.Value;

public interface AdPreviewProjectionDto {
    String getId();
    String getTitle();
    double getPrice();

    @Value("#{target.images.size() > 0 ? target.images[0] : null}")
    String getFirstImage();
    boolean isSold();
    int getVisibility();
}
