package com.opensell.service;

import com.opensell.entities.Customer;
import com.opensell.entities.customer.CustomerInfo;
import com.opensell.entities.dto.CustomerModificationView;
import com.opensell.repository.CustomerInfoRepository;
import com.opensell.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * This repository allows to get infos from a customer by a provided unique user link by querying the database
 */

@Service
public class CustomerService {

    @Autowired
    private LoginRepository rep;

    @Autowired
    private CustomerInfoRepository customerInfoRep;

    public CustomerModificationView getModificationView(int id) {
        Customer c = rep.findCustomerByIdCustomer(id);
        CustomerInfo info = c.getCustomerInfo();
        return new CustomerModificationView(c.getUsername(), info.getFirstName(), info.getLastName(),
                info.getExposedEmail(), info.getBio(), info.getIconPath());
    }

    public int checkUsername(String username) {
        return rep.countByUsername(username);
    }

    public int checkSamePwd(int customerId, String pwd) {
        return rep.checkSamePwd(customerId, pwd);
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
