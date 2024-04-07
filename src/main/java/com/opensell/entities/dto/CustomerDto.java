package com.opensell.entities.dto;

import com.opensell.entities.Ad;
import com.opensell.entities.Customer;
import com.opensell.entities.ad.AdImage;
import com.opensell.entities.ad.AdType;
import com.opensell.entities.customer.CustomerInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Record that contain the essential data that a customer need to see
 * when clicking on ad.
 *
 * @author Achraf
 */
@Data @NoArgsConstructor
public class CustomerDto {
    public CustomerInfo customerInfo;
    public int customerId;
	public String username;
    public String link;

	public CustomerDto(Customer customer) {
		if(customer != null) {
			this.customerInfo = customer.getCustomerInfo();
			this.customerId = customer.getIdCustomer();
			this.username = customer.getUsername();
			this.link = customer.getLink();
		}
	}
}