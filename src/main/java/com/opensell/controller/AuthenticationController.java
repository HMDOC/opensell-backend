package com.opensell.controller;

import com.opensell.repository.CustomerRepository;
import com.opensell.service.CodeService;
import com.opensell.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(value = "http://localhost/")
public class AuthenticationController {

    int code;

    @Autowired
    private CustomerRepository rep;

    @Autowired
    private EmailService emailService;

    @Autowired
    private CodeService codeService;

    @GetMapping("/login")
    public int login(@RequestParam String username, @RequestParam String pwd) {
        if (rep.checkUsernameOrPersonalEmail(username) ==  1) {
            //return rep.checkPassword(usernameOrEmail, pwd) -> (1 ou 0?)
            if (rep.checkPassword(username, pwd) == 1) return 1; // Correct password
        }
        return 2;
    }

    @PostMapping("/signup")
    public int signup(@RequestParam String email, @RequestParam String username, @RequestParam String pwd) {
        if (rep.countByPersonalEmail(email) == 1) {
            return 1; // Email already exists
        } else if (rep.countByUsername(username) == 1) {
            return 2; // Username already exists
        } else {
            code = codeService.generateCode();
            if (emailService.sendEmail(email, "Welcome to OpenSell", "Thank you for signing up with OpenSell! Here's your verification code:\n" + code)) {
                return 3; // Email sent
            };
            return 4; // Email not sent
        }
    }
    @PostMapping("/signup")
    public boolean verifyCode(@RequestParam String codeInput) {
        if (code == Integer.parseInt(codeInput)) {
            return true;
        }
        return false;
    }
}
