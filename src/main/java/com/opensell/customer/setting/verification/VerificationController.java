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

    @GetMapping("/username")
    public int checkUsername(@RequestParam String username) {
        return verificationService.checkUsername(username);
    }

    @GetMapping("/private-email")
    public int checkPersonalEmail(@RequestParam String email) {
        return verificationService.checkPersonalEmail(email);
    }

    @GetMapping("/phone-number")
    public int checkPhoneNumber(@RequestParam String phoneNumber) {
        return verificationService.checkPhoneNumber(phoneNumber);
    }

    @GetMapping("/same-pwd")
    public int checkSamePwd(@RequestParam int id, @RequestParam String pwd) {
        return verificationService.checkSamePwd(id, pwd);
    }
}
