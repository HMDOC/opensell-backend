package com.opensell.customer.auth;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @GetMapping
    public CustomerDto getCustomerDto(@RequestParam String idCustomer) {
        return authService.getCustomerDto(idCustomer);
    }

    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String pwd) {
        return authService.login(username, pwd);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestParam String email, @RequestParam String username, @RequestParam String pwd) throws MessagingException {
        return authService.signup(email, username, pwd);
    }

    @GetMapping("/verify-code")
    public int verifyCode(@RequestParam String email, @RequestParam String inputCode) {
        return authService.verifyCode(email, inputCode);
    }
}
