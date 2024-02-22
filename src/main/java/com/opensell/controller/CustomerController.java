package com.opensell.controller;

import java.util.ArrayList;
import java.util.List;

import com.opensell.service.EmailService;
import com.opensell.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.opensell.entities.Customer;
import com.opensell.entities.customer.CustomerInfo;
import com.opensell.entities.dto.CustomerModificationPagePlaceholder;
import com.opensell.repository.CustomerRepository;

@RestController
@CrossOrigin(value = "http://localhost/")
@RequestMapping("/user")
public class CustomerController {

    @Autowired
    private UserService service;

    @GetMapping("/get-customer-modification-placeholder/{link}")
    public CustomerModificationPagePlaceholder getPlaceHolder(@PathVariable String link) {
        return service.getPlaceHolder(link);
    }

    //ADDSOCIAL LINK
    @PostMapping("/add-social-link")
    public ResponseEntity<String> addSocialLink(@RequestParam int id, @RequestParam String link) {
        return service.addSocialLink(id, link);
    }

}
