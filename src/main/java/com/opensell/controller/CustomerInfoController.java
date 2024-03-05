package com.opensell.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.opensell.entities.customer.CustomerInfo;
import com.opensell.repository.CustomerInfoRepository;

@RestController
@CrossOrigin(value = "http://localhost/")
public class CustomerInfoController {
    
    @Autowired
    private CustomerInfoRepository customerInfoRepository;

    @GetMapping("/user/{link}")
    public CustomerInfo getCustomerInfo(@PathVariable String link) {
        return customerInfoRepository.findCustomerInfoByLink(link);
    }
}
