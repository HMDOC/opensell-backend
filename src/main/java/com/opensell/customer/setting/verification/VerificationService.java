package com.opensell.customer.setting.verification;

import com.opensell.model.Customer;
import com.opensell.repository.CustomerInfoRepository;
import com.opensell.repository.LoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerificationService {
    private final PasswordEncoder passwordEncoder;
    private final LoginRepository loginRepository;
    private final CustomerInfoRepository customerInfoRepository;

    public int checkUsername(String username) {
        return loginRepository.countByUsername(username);
    }

    public int checkSamePwd(int customerId, String pwd) {
        Customer c = loginRepository.findCustomerById(customerId);
        if (passwordEncoder.matches(pwd, c.getPwd())) return 1;
        return 0;
    }

    public int checkPersonalEmail(String email) {
        return loginRepository.countByEmail(email);
    }

    public int checkPhoneNumber(String phoneNumber) {
        return customerInfoRepository.countByPhoneNumber(phoneNumber);
    }
}
