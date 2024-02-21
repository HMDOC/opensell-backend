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

    @Autowired
    private EmailService emailService;

    @PostMapping("/signup")
    public int signup(@RequestParam String email, @RequestParam String username, @RequestParam String pwd) {
        if (signupRepository.checkEmail(email) == 1) {
            return 1; // Email already exists
        } else if (signupRepository.checkUsername(username) == 1) {
            return 2; // Username already exists
        } else {
            if (emailService.sendEmail(email, "Welcome to OpenSell", "Thank you for signing up with OpenSell!")) {
                return 3; // Email sent
            };
            return 4; // Email not sent
        }
    }
}
