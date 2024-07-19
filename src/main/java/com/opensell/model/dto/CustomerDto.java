package com.opensell.model.dto;

import com.opensell.model.Customer;
import com.opensell.model.customer.CustomerInfo;

import lombok.Data;
import lombok.NoArgsConstructor;

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
	public String personalEmail;

	public CustomerDto(Customer customer) {
		if(customer != null) {
			this.customerInfo = customer.getCustomerInfo();
			this.customerId = customer.getIdCustomer();
			this.username = customer.getUsername();
			this.personalEmail = customer.getPersonalEmail();
		}
	}
}