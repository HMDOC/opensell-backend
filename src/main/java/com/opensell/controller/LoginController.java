package com.opensell.controller;

import org.springframework.web.bind.annotation.RestController;

import com.opensell.repository.LoginRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Quoc Dung
 */

@CrossOrigin("http://localhost/")
@RestController
public class LoginController {

    @Autowired
    private LoginRepository loginRepository;

    @GetMapping("/login")
    public int login(@RequestParam String username, @RequestParam String pwd) {
        if (loginRepository.checkEmailUsername(username) ==  1) {
            if (loginRepository.checkPassword(username, pwd) == 1) return 1; // Correct password
        }
        return 2;
    }
}
