package com.opensell.model.dto;

import java.sql.Date;
import java.util.List;

import com.opensell.ad.catalog.dto.AdPreviewDto;
import com.opensell.model.Ad;
import com.opensell.model.Customer;
import com.opensell.model.customer.CustomerInfo;

public class CustomerProfile {
    public String username;
    public Date joinedDate;
    public CustomerInfo customerInfo;
    public List<AdPreviewDto> ads;

    public CustomerProfile(Customer customer) {
        this.username = customer.getUsername();
        this.joinedDate = customer.getJoinedDate();
        this.customerInfo = customer.getCustomerInfo();
        this.ads = customer.getAds().stream().map(Ad::toAdSearchPreview).toList();
    }
}
