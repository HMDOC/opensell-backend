package com.opensell.entities.dto;

import java.sql.Date;
import java.util.List;
import com.opensell.entities.Customer;
import com.opensell.entities.customer.CustomerInfo;

public class CustomerProfil {
    public String username;
    public Date joinedDate;
    public CustomerInfo customerInfo;
    public List<AdSearchPreview> ads;

    public CustomerProfil(Customer customer, List<AdSearchPreview> ads) {
        this.username = customer.getUsername();
        this.joinedDate = customer.getJoinedDate();
        this.customerInfo = customer.getCustomerInfo();
        this.ads = ads;
    }
}
