package com.opensell.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.opensell.repository.CustomerModificationRepository;

/**
 * @author Olivier
 * @Note ex: http://localhost:9108/change/nouveauusername/35/change-username
 */
@RestController
@CrossOrigin("http://localhost")
@RequestMapping(value = "/change")
public class CustomerModificationController {

    @Autowired
    private CustomerModificationRepository rep;

    @PatchMapping("/change-private-email")
    public int changeCustomerPersonalEmail(@RequestParam String email, @RequestParam int id) {
        return rep.updateCustomerPersonalEmail(email, id);
    }

    @PatchMapping("/change-username")
    public int changeCustomerUsername(@RequestParam String username, @RequestParam int id) {
        return rep.updateCustomerUsername(username, id);
    }

    @PatchMapping("/change-pwd")
    public int changeCustomerPwd(@RequestParam String pwd, @RequestParam int id) {
        return rep.updateCustomerPwd(pwd, id);
    }

    @PatchMapping("/change-first-name")
    public int changeCustomerFirstName(@RequestParam String firstName, @RequestParam int id) {
        return rep.updateCustomerFirstName(firstName, id);
    }

    @PatchMapping("/change-last-name")
    public int changeCustomerLastName(@RequestParam String lastName, @RequestParam int id) {
        return rep.updateCustomerLastName(lastName, id);
    }

    @PatchMapping("/change-phone-number")
    public int changeCustomerPhoneNumber(@RequestParam String phoneNumber, @RequestParam int id) {
        return rep.updateCustomerPhoneNumber(phoneNumber, id);
    }

    @PatchMapping("/change-primary-address")
    public int changeCustomerPrimaryAddress(@RequestParam String address, @RequestParam int id) {
        return rep.updateCustomerPrimaryAddress(address, id);
    }

    @PatchMapping("/change-icon-path")
    public int changeCustomerIconPath(@RequestParam String iconPath, @RequestParam int id) {
        return rep.updateCustomerIconPath(iconPath, id);
    }

    @PatchMapping("/change-bio")
    public int changeCustomerBio(@RequestParam String bio, @RequestParam int id) {
        return rep.updateCustomerBio(bio, id);
    }

    @PatchMapping( "/change-public-email")
    public int changeCustomerExposedEmail(@RequestParam String email, @RequestParam int id) {
        return rep.updateCustomerExposedEmail(email, id);
    }

    @PatchMapping("/change-socials")
    public int changeCustomerSocialLink(@RequestParam String link, @RequestParam int id, @RequestParam String oldLink) {
        return rep.updateCustomerSocialLink(link, id, oldLink);
    }


}
