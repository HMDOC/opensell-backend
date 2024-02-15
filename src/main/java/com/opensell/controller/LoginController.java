package com.opensell.controller;

import org.springframework.web.bind.annotation.RestController;

import com.opensell.entities.Customer;
import com.opensell.repository.LoginRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Quoc Dung
 */

@CrossOrigin("http://localhost/")
@RestController
public class LoginController {

    @Autowired
    private LoginRepository loginRepository;

    @PostMapping("/login")
    public boolean login(@RequestBody Customer c) {
        int count = loginRepository.findUserLogin(c.getUsername(), c.getPwd());
        return count > 0;
    }

    

}
