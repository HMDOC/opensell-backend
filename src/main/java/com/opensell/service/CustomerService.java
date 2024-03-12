package com.opensell.service;

import com.opensell.entities.Customer;
import com.opensell.entities.customer.CustomerInfo;
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

    public CustomerModificationView getPlaceHolder(@PathVariable String link) {
        Customer c = rep.findCustomerByLink(link);
        CustomerInfo info = c.getCustomerInfo();
        List<String> social_links = new ArrayList<>();
        c.getCustomerInfo().getSocials().forEach(elem -> {
            social_links.add(elem.getLink());
        });
        return new CustomerModificationView(c.getUsername(), info.getFirstName(), info.getLastName(),
                info.getExposedEmail(), info.getPrimaryAddress(), info.getBio(), info.getIconPath(), social_links);
    }

    public ModificationFeedback addSocialLink(@RequestParam String customer_link, @RequestParam String link) {
        if (customerInfoRep.countSocialLinkByCustomerLink(customer_link) < 5) {
            return new ModificationFeedback(CustomerModificationCode.OK, customerInfoRep.addSocialLink(customer_link, link), link);
        };
        return new ModificationFeedback(CustomerModificationCode.OTHER_ERROR, 0, link);
    }

    public int checkUsername(String username) {
        return rep.countByUsername(username);
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
