package com.opensell.service;

import com.opensell.entities.verification.RegexVerification;
import com.opensell.repository.CustomerModificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Oliver Mansuy
 */
@Service
public class CustomerModificationService {

    @Autowired
    private CustomerModificationRepository rep;

    public int changePersonalEmail(String email, int id) {
        return RegexVerification.EMAIL.verify(email) ? rep.updateCustomerPersonalEmail(email, id) : 0; //wrong format
    }

    public int changeExposedEmail(String email, int id) {
        return RegexVerification.EMAIL.verify(email) ? rep.updateCustomerExposedEmail(email, id) : 0; //wrong format
    }

    public int changeFirstName(String firstName, int id) {
        return RegexVerification.NAME.verify(firstName) ? rep.updateCustomerFirstName(firstName, id) : 0;
    }

    public int changeLastName(String lastName, int id) {
        return RegexVerification.NAME.verify(lastName) ? rep.updateCustomerLastName(lastName, id) : 0;
    }

}
