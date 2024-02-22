package com.opensell.service;

import com.opensell.entities.Customer;
import com.opensell.entities.customer.CustomerInfo;
import com.opensell.entities.dto.CustomerModificationPagePlaceholder;
import com.opensell.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private CustomerRepository rep;

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

    public ResponseEntity<String> addSocialLink(@RequestParam int id, @RequestParam String link) {
        if (rep.countSocialLinkById(id) < 5) rep.addSocialLink(id, link);
        return new ResponseEntity<>("...", HttpStatus.CREATED);
    }


}
