package com.opensell.customer;

import java.time.LocalDateTime;
import java.util.List;
import com.opensell.ad.catalog.dto.AdPreviewDto;
import com.opensell.model.Ad;
import com.opensell.model.Customer;

public record ProfileDto(
    String firstName,
    String lastName,
    String bio,
    String iconPath,
    String username,
    LocalDateTime joinedDate,
    List<AdPreviewDto> ads
) {
    public ProfileDto(Customer customer) {
        this(
            customer.getFirstName(),
            customer.getLastName(),
            customer.getBio(),
            customer.getIconPath(),
            customer.getUsername(),
            customer.getJoinedDate(),
            null
            // REDO
            //customer.getAds().stream().map(Ad::toAdSearchPreview).toList()
        );
    }
}
