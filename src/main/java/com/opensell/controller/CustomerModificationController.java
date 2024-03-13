package com.opensell.controller;

import com.opensell.service.CustomerModificationService;
import com.opensell.service.customerModification.ModificationFeedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Olivier
 */
@RestController //extends @RequestBody, which enables automatic serialization of returned values of controllers
@CrossOrigin("http://localhost")
@RequestMapping("/change")
public class CustomerModificationController {

    @Autowired
    private CustomerModificationService service;

    @PutMapping("/change-private-email")
    public ModificationFeedback changeCustomerPersonalEmail(@RequestParam String email, @RequestParam String link) {
        return service.changePersonalEmail(email, link);
    }

    @PutMapping("/change-username")
    public ModificationFeedback changeCustomerUsername(@RequestParam String username, @RequestParam String link) {
        return service.changeUsername(username, link);
    }

    @PutMapping("/change-pwd")
    public ModificationFeedback changeCustomerPwd(@RequestParam String pwd, @RequestParam String link) {
        return service.changePwd(pwd, link);
    }

    @PutMapping("/change-first-name")
    public ModificationFeedback changeCustomerFirstName(@RequestParam String firstName, @RequestParam String link) {
        return service.changeFirstName(firstName, link);
    }

    @PutMapping("/change-last-name")
    public ModificationFeedback changeCustomerLastName(@RequestParam String lastName, @RequestParam String link) {
        return service.changeLastName(lastName, link);
    }

    @PutMapping("/change-phone-number")
    public ModificationFeedback changeCustomerPhoneNumber(@RequestParam String phoneNumber, @RequestParam String link) {
        return service.changePhoneNumber(phoneNumber, link);
    }

    @PutMapping("/change-primary-address")
    public ModificationFeedback changeCustomerPrimaryAddress(@RequestParam String address, @RequestParam String link) {
        return service.changePrimaryAddress(address, link);
    }

    @PutMapping("/change-icon-path")
    public ModificationFeedback changeCustomerIconPath(@RequestParam String iconPath, @RequestParam String link) {
        return service.changeIconPath(iconPath, link);
    }

    @PutMapping("/change-bio")
    public ModificationFeedback changeCustomerBio(@RequestParam String bio, @RequestParam String link) {
        return service.changeBio(bio, link);
    }

    @PutMapping( "/change-public-email")
    public ModificationFeedback changeCustomerExposedEmail(@RequestParam String email, @RequestParam String link) {
        return service.changeExposedEmail(email, link);
    }

    @PutMapping("/change-socials-1")
    public ModificationFeedback changeCustomerSocials1(@RequestParam String link, @RequestParam String cLink) {
        return service.changeSocials1(link, cLink);
    }

    @PutMapping("/change-socials-2")
    public ModificationFeedback changeCustomerSocials2(@RequestParam String link, @RequestParam String cLink) {
        return service.changeSocials2(link, cLink);
    }

    @PutMapping("/change-socials-3")
    public ModificationFeedback changeCustomerSocials3(@RequestParam String link, @RequestParam String cLink) {
        return service.changeSocials3(link, cLink);
    }

    @PutMapping("/change-socials-4")
    public ModificationFeedback changeCustomerSocials4(@RequestParam String link, @RequestParam String cLink) {
        return service.changeSocials4(link, cLink);
    }

    @PutMapping("/change-socials-5")
    public ModificationFeedback changeCustomerSocials5(@RequestParam String link, @RequestParam String cLink) {
        return service.changeSocials5(link, cLink);
    }


}
