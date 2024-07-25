package com.opensell.customer.setting.verification;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customer/setting/verification")
@RequiredArgsConstructor
public class VerificationController {
    private final VerificationService verificationService;

    @GetMapping("/username/exists")
    public boolean isUsernameExists(@RequestParam int id, @RequestParam String username) {
        return verificationService.isUsernameExists(id, username);
    }

    @GetMapping("/email/exists")
    public boolean isEmailExists(@RequestParam int id, @RequestParam String email) {
        return verificationService.isEmailExists(id, email);
    }
}
