package com.opensell.customer;

import com.opensell.ad.AdRepository;
import com.opensell.model.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final AdRepository adRepository;

    public ProfileDto getProfileDto(String username) {
        Customer customer = customerRepository.findOneByUsernameAndIsDeletedFalseAndIsActivatedTrue(username);
        return customer != null ? new ProfileDto(customer, adRepository.getProfileAds(customer.getId())) : null;
    }
}
