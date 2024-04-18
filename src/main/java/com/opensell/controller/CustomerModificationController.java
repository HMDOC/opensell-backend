package com.opensell.controller;

import com.opensell.entities.dto.CustomerModificationData;
import com.opensell.service.CustomerModificationService;
import com.opensell.service.customerModification.ModificationFeedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Olivier
 */
@RestController //extends @RequestBody, which enables automatic serialization of returned values of controllers
@CrossOrigin("${allowedUrl}")
@RequestMapping("/change")
public class CustomerModificationController {

    @Autowired
    private CustomerModificationService service;

    @PutMapping("/change-private-email")
    public ModificationFeedback changeCustomerPersonalEmail(@RequestBody CustomerModificationData data) {
        return service.changePersonalEmail(data);
    }

    @PutMapping("/change-username")
    public ModificationFeedback changeCustomerUsername(@RequestBody CustomerModificationData data) {
        return service.changeUsername(data);
    }

    @PutMapping("/change-pwd")
    public ModificationFeedback changeCustomerPwd(@RequestBody CustomerModificationData data) {
        return service.changePwd(data);
    }

    @PutMapping("/change-first-name")
    public ModificationFeedback changeCustomerFirstName(@RequestBody CustomerModificationData data) {
        return service.changeFirstName(data);
    }

    @PutMapping("/change-last-name")
    public ModificationFeedback changeCustomerLastName(@RequestBody CustomerModificationData data) {
        return service.changeLastName(data);
    }

    @PutMapping("/change-phone-number")
    public ModificationFeedback changeCustomerPhoneNumber(@RequestBody CustomerModificationData data) {
        return service.changePhoneNumber(data);
    }

    @PutMapping("/change-primary-address")
    public ModificationFeedback changeCustomerPrimaryAddress(@RequestBody CustomerModificationData data) {
        return service.changePrimaryAddress(data);
    }

    @PutMapping("/change-icon-path")
    public ModificationFeedback changeCustomerIconPath(@RequestBody CustomerModificationData data) {
        return service.changeIconPath(data);
    }

    @PutMapping("/change-bio")
    public ModificationFeedback changeCustomerBio(@RequestBody CustomerModificationData data) {
        return service.changeBio(data);
    }

    @PutMapping( "/change-public-email")
    public ModificationFeedback changeCustomerExposedEmail(@RequestBody CustomerModificationData data) {
        return service.changeExposedEmail(data);
    }

    @PutMapping("/change-socials-1")
    public ModificationFeedback changeCustomerSocials1(@RequestBody CustomerModificationData data) {
        return service.changeSocials1(data);
    }

    @PutMapping("/change-socials-2")
    public ModificationFeedback changeCustomerSocials2(@RequestBody CustomerModificationData data) {
        return service.changeSocials2(data);
    }

    @PutMapping("/change-socials-3")
    public ModificationFeedback changeCustomerSocials3(@RequestBody CustomerModificationData data) {
        return service.changeSocials3(data);
    }

    @PutMapping("/change-socials-4")
    public ModificationFeedback changeCustomerSocials4(@RequestBody CustomerModificationData data) {
        return service.changeSocials4(data);
    }

    @PutMapping("/change-socials-5")
    public ModificationFeedback changeCustomerSocials5(@RequestBody CustomerModificationData data) {
        return service.changeSocials5(data);
    }


}
