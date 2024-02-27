package com.opensell.controller;

import com.opensell.service.CustomerModificationService;
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
    public ResponseEntity<Integer> changeCustomerPersonalEmail(@RequestParam String email, @RequestParam int id) {
        return service.changePersonalEmail(email, id);
    }

    @PutMapping("/change-username")
    public ResponseEntity<Integer> changeCustomerUsername(@RequestParam String username, @RequestParam int id) {
        return service.changeUsername(username, id);
    }

    @PutMapping("/change-pwd")
    public ResponseEntity<Integer> changeCustomerPwd(@RequestParam String pwd, @RequestParam int id) {
        return service.changePwd(pwd, id);
    }

    @PutMapping("/change-first-name")
    public ResponseEntity<Integer> changeCustomerFirstName(@RequestParam String firstName, @RequestParam int id) {
        return service.changeFirstName(firstName, id);
    }

    @PutMapping("/change-last-name")
    public ResponseEntity<Integer> changeCustomerLastName(@RequestParam String lastName, @RequestParam int id) {
        return service.changeLastName(lastName, id);
    }

    @PutMapping("/change-phone-number")
    public ResponseEntity<Integer> changeCustomerPhoneNumber(@RequestParam String phoneNumber, @RequestParam int id) {
        return service.changePhoneNumber(phoneNumber, id);
    }

    @PutMapping("/change-primary-address")
    public ResponseEntity<Integer> changeCustomerPrimaryAddress(@RequestParam String address, @RequestParam int id) {
        return service.changePrimaryAddress(address, id);
    }

    @PutMapping("/change-icon-path")
    public ResponseEntity<Integer> changeCustomerIconPath(@RequestParam String iconPath, @RequestParam int id) {
        return service.changeIconPath(iconPath, id);
    }

    @PutMapping("/change-bio")
    public ResponseEntity<Integer> changeCustomerBio(@RequestParam String bio, @RequestParam int id) {
        return service.changeBio(bio, id);
    }

    @PutMapping( "/change-public-email")
    public ResponseEntity<Integer> changeCustomerExposedEmail(@RequestParam String email, @RequestParam int id) {
        return service.changeExposedEmail(email, id);
    }

    @PutMapping("/change-socials")
    public ResponseEntity<Integer> changeCustomerSocialLink(@RequestParam String link, @RequestParam int id, @RequestParam String oldLink) {
        return service.changeSocialLink(link, id, oldLink);
    }


}
