package com.opensell.service;

import com.opensell.model.Customer;
import com.opensell.repository.CustomerInfoRepository;
import com.opensell.repository.LoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * This repository allows to get infos from a customer by a provided unique user link by querying the database
 */
@Service
@RequiredArgsConstructor
public class CustomerService {
    private final LoginRepository rep;
    private final CustomerInfoRepository customerInfoRep;
    private final PasswordEncoder passwordEncoder;

    public int checkUsername(String username) {
        return rep.countByUsername(username);
    }

    public int checkSamePwd(int customerId, String pwd) {
        Customer c = rep.findCustomerById(customerId);
        if (passwordEncoder.matches(pwd, c.getPwd())) return 1;
        return 0;
    }

    public int checkPersonalEmail(String email) {
        return rep.countByPersonalEmail(email);
    }

    public int checkExposedEmail(String email) {
        return customerInfoRep.countByExposedEmail(email);
    }

    public int checkPhoneNumber(String phoneNumber) {
        return customerInfoRep.countByPhoneNumber(phoneNumber);
    }
}
