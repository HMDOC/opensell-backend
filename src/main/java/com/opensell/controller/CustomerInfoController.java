package com.opensell.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.opensell.model.dto.CustomerProfil;
import com.opensell.model.Ad;
import com.opensell.model.Customer;
import com.opensell.repository.AdRepository;
import com.opensell.repository.CustomerRepository;

@RestController
@CrossOrigin("${allowedUrl}")
@RequestMapping("/user")
@RequiredArgsConstructor
public class CustomerInfoController {
    private final CustomerRepository customerRepository;
    private final AdRepository adRep;

    @GetMapping("/{link}")
    public CustomerProfil getCustomerInfo(@PathVariable String link) {
        Customer customer = customerRepository.findOneByLinkAndIsDeletedFalseAndIsActivatedTrue(link);

        if(customer != null) {
            return new CustomerProfil(customer, adRep.getAdsFromUser(link).stream().map(Ad::toAdSearchPreview).toList());
        }

        else return null;
    }
}
