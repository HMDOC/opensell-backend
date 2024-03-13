package com.opensell.service;

import com.opensell.entities.Customer;
import com.opensell.entities.customer.CustomerInfo;
import com.opensell.entities.customer.CustomerSocials;
import com.opensell.entities.dto.CustomerModificationView;
import com.opensell.repository.CustomerInfoRepository;
import com.opensell.repository.LoginRepository;
import com.opensell.service.customerModification.CustomerModificationCode;
import com.opensell.service.customerModification.ModificationFeedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.ArrayList;
import java.util.List;

/**
 * This repository allows to get infos from a customer by a provided unique user link by querying the database
 */

@Service
public class CustomerService {

    @Autowired
    private LoginRepository rep;

    @Autowired
    private CustomerInfoRepository customerInfoRep;

    public CustomerModificationView getModificationView(@PathVariable String link) {
        Customer c = rep.findCustomerByLink(link);
        CustomerInfo info = c.getCustomerInfo();
        CustomerSocials cs = customerInfoRep.findCustomerSocialsByLink(link);
        return new CustomerModificationView(c.getUsername(), info.getFirstName(), info.getLastName(),
                info.getExposedEmail(), info.getPrimaryAddress(), info.getBio(), info.getIconPath(),
                cs.getLink1(), cs.getLink2(), cs.getLink3(), cs.getLink4(), cs.getLink5());
    }

    public int checkUsername(String username) {
        return rep.countByUsername(username);
    }

    public int checkSamePwd(String cLink, String pwd) {
        return rep.checkSamePwd(cLink, pwd);
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
