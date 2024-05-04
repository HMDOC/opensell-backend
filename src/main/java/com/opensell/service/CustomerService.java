package com.opensell.service;

import com.opensell.entities.Customer;
import com.opensell.repository.CustomerInfoRepository;
import com.opensell.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * This repository allows to get infos from a customer by a provided unique user link by querying the database
 */

@Service
public class CustomerService {

    @Autowired
    private LoginRepository rep;

    @Autowired
    private CustomerInfoRepository customerInfoRep;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public int checkUsername(String username) {
        return rep.countByUsername(username);
    }

    public int checkSamePwd(int customerId, String pwd) {
        Customer c = rep.findCustomerByIdCustomer(customerId);
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
