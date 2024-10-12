package com.opensell.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.opensell.entities.Ad;
import com.opensell.entities.Customer;
import com.opensell.entities.dto.CustomerProfil;
import com.opensell.repository.AdRepository;
import com.opensell.repository.CustomerRepository;

@RestController
@RequestMapping("/user")
public class CustomerInfoController {
    
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AdRepository adRep;

    @GetMapping("/{link}")
    public CustomerProfil getCustomerInfo(@PathVariable String link) {
        Customer customer = customerRepository.findOneByLinkAndIsDeletedFalseAndIsActivatedTrue(link);

        if(customer != null) {
            return new CustomerProfil(customer, adRep.getAdsFromUser(link).stream().map(Ad::toAdSearchPreview).toList());
        }

        else return null;
    }
}
