package com.opensell.customer.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @GetMapping
    public CustomerDto getCustomerDto(@RequestParam int idCustomer) {
        return authService.getCustomerDto(idCustomer);
    }

    @GetMapping("/login")
    public Integer login(@RequestParam String username, @RequestParam String pwd) {
        return authService.login(username, pwd);
    }

    @PostMapping("/signup")
    public int signup(@RequestParam String email, @RequestParam String username, @RequestParam String pwd) {
        return authService.signup(email, username, pwd);
    }

    @GetMapping("/verify-code")
    public int verifyCode(@RequestParam String email, @RequestParam String inputCode) {
        return authService.verifyCode(email, inputCode);
    }
}
