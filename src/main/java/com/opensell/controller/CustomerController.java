package com.opensell.controller;

import com.opensell.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("${allowedUrl}")
@RequestMapping("/c")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService service;

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

    @GetMapping("/check-same-pwd")
    public int checkSamePwd(@RequestParam int id, @RequestParam String pwd) {
        return service.checkSamePwd(id, pwd);
    }
}
