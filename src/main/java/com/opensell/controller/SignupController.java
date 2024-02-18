package com.opensell.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.opensell.repository.SignupRepository;

/**
 * @author Quoc Dung
 */

 @CrossOrigin("http://localhost/")
 @RestController

public class SignupController {
    @Autowired
    private SignupRepository signupRepository;

    @PostMapping("/signup")
    public int signup(@RequestParam String email, @RequestParam String username, @RequestParam String pwd) {
        if (signupRepository.checkEmail(email) == 1) {
            return 1; // Email already exists
        } else if (signupRepository.checkUsername(username) == 1) {
            return 2; // Username already exists
        } else {
            // creer le nouveau utilisateur ici
            return 3; // User created
        }
    }
}
