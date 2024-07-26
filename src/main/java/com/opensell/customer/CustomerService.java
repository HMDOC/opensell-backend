package com.opensell.customer;

import com.opensell.model.Customer;
import com.opensell.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public ProfileDto getProfileDto(String username) {
        Customer customer = customerRepository.findOneByUsernameAndIsDeletedFalseAndIsActivatedTrue(username);
        return customer != null ? new ProfileDto(customer) : null;
    }
}
