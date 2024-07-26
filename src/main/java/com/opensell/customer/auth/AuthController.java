package com.opensell.customer.auth;

import com.opensell.model.dto.CustomerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @GetMapping("/login")
    public Integer login(@RequestParam String username, @RequestParam String pwd) {
        return authService.login(username, pwd);
    }

    @GetMapping("/getDto")
    public CustomerDto getCustomerDto(@RequestParam int idCustomer) {
        return authService.getCustomerDto(idCustomer);
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
