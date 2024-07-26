package com.opensell.customer.auth;

import com.opensell.model.Customer;

/**
 * Record that contain the essential data that a customer need to see
 * when clicking on ad.
 *
 * @author Achraf
 */
public record CustomerDto(
	String firstName,
	String lastName,
	String bio,
	String iconPath,
	int customerId,
	String username,
	String email
) {
	public CustomerDto(Customer customer) {
		this(
			customer.getFirstName(),
			customer.getLastName(),
			customer.getBio(),
			customer.getIconPath(),
			customer.getId(),
			customer.getUsername(),
			customer.getEmail()
		);
	}
}