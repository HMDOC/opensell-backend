package com.opensell.customer;

import java.time.LocalDateTime;
import java.util.List;
import com.opensell.ad.catalog.dto.AdPreviewDto;
import com.opensell.model.Ad;
import com.opensell.model.Customer;
import com.opensell.model.customer.CustomerInfo;

public record ProfileDto(
    String username,
    LocalDateTime joinedDate,
    CustomerInfo customerInfo,
    List<AdPreviewDto> ads
) {
    public ProfileDto(Customer customer) {
        this(
            customer.getUsername(),
            customer.getJoinedDate(),
            customer.getCustomerInfo(),
            customer.getAds().stream().map(Ad::toAdSearchPreview).toList()
        );
    }
}
