package com.opensell.controller;

import com.opensell.service.CustomerService;
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
    public ResponseEntity<String> addSocialLink(@RequestParam int id, @RequestParam String link) {
        return service.addSocialLink(id, link);
    }

}
