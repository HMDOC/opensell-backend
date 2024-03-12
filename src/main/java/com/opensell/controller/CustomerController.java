package com.opensell.controller;

import com.opensell.service.CustomerService;
import com.opensell.service.customerModification.ModificationFeedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.opensell.entities.dto.CustomerModificationView;


@RestController
@CrossOrigin(value = "http://localhost/")
@RequestMapping("/c")
public class CustomerController {

    @Autowired
    private CustomerService service;

    @GetMapping("/get-customer-modification-view/{link}")
    public CustomerModificationView getPlaceHolder(@PathVariable String link) {
        return service.getPlaceHolder(link);
    }

    @PostMapping("/add-social-link")
    public ModificationFeedback addSocialLink(@RequestParam String customerLink, @RequestParam String link) {
        return service.addSocialLink(customerLink, link);
    }

    @GetMapping("/check-username")
    public int checkUsername(@RequestParam String username) {
        return service.checkUsername(username);
    }

    @GetMapping("/check-private-email")
    public int checkPersonalEmail(@RequestParam String email) {
        return service.checkPersonalEmail(email);
    }

    @GetMapping("/check-public-email")
    public int checkExposedEmail(@RequestParam String email) {
        return service.checkExposedEmail(email);
    }

    @GetMapping("/check-phone-number")
    public int checkPhoneNumber(@RequestParam String phoneNumber) {
        return service.checkPhoneNumber(phoneNumber);
    }

}
