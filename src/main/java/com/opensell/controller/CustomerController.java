package com.opensell.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.opensell.entities.Customer;
import com.opensell.entities.customer.CustomerInfo;
import com.opensell.entities.dto.CustomerModificationPagePlaceholder;
import com.opensell.repository.CustomerRepository;

@RestController
@CrossOrigin(value = "http://localhost/")
@RequestMapping("/user")
public class CustomerController {

    @Autowired
    private CustomerRepository rep;

    @GetMapping("/get-customer-modification-placeholder/{link}")
    public CustomerModificationPagePlaceholder getPlaceHolder(@PathVariable String link) {
        Customer c = rep.findCustomerByLink(link);
        CustomerInfo info = c.getCustomerInfo();
        List<String> social_links = new ArrayList<>();
        c.getCustomerInfo().getSocials().forEach(elem -> {
            social_links.add(elem.getLink());
        });
        return new CustomerModificationPagePlaceholder(c.getUsername(), info.getFirstName(), info.getLastName(),
                info.getExposedEmail(), info.getPrimaryAddress(), info.getBio(), info.getIconPath(), social_links);
    }
}
