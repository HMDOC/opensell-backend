package com.opensell.customer;

import java.time.LocalDateTime;
import java.util.List;
import com.opensell.ad.catalog.dto.AdPreviewDto;
import com.opensell.ad.listings.dto.AdPreviewProjectionDto;
import com.opensell.model.Ad;
import com.opensell.model.Customer;

public record ProfileDto(
    String firstName,
    String lastName,
    String bio,
    String iconPath,
    String username,
    LocalDateTime joinedDate,
    List<AdPreviewProjectionDto> ads
) {
    public ProfileDto(Customer customer, List<AdPreviewProjectionDto> ads) {
        this(
            customer.getFirstName(),
            customer.getLastName(),
            customer.getBio(),
            customer.getIconPath(),
            customer.getUsername(),
            customer.getJoinedDate(),
            ads
        );
    }
}
