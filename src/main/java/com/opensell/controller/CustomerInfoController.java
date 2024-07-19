package com.opensell.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.opensell.model.dto.CustomerProfile;
import com.opensell.model.Customer;
import com.opensell.repository.AdRepository;
import com.opensell.repository.CustomerRepository;

@RestController
@CrossOrigin("${allowedUrl}")
@RequestMapping("/user")
@RequiredArgsConstructor
public class CustomerInfoController {
    private final CustomerRepository customerRepository;

    @GetMapping("/{username}")
    public CustomerProfile getCustomerInfo(@PathVariable String username) {
        Customer customer = customerRepository.findOneByUsernameAndIsDeletedFalseAndIsActivatedTrue(username);

        if(customer != null) {
            return new CustomerProfile(customer);
        }

        return null;
    }
}
