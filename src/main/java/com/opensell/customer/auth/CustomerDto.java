package com.opensell.customer.auth;

import com.opensell.model.Customer;
import com.opensell.model.customer.CustomerInfo;

/**
 * Record that contain the essential data that a customer need to see
 * when clicking on ad.
 *
 * @author Achraf
 */
public record CustomerDto(CustomerInfo customerInfo, int customerId, String username, String email) {
	public CustomerDto(Customer customer) {
		this(
			customer.getCustomerInfo(),
			customer.getId(),
			customer.getUsername(),
			customer.getEmail()
		);
	}
}