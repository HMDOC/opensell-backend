package com.opensell.controller;

import com.opensell.repository.CustomerModificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Olivier
 */
@RestController
@CrossOrigin("http://localhost")
@RequestMapping("/change")
public class CustomerModificationController {

    @Autowired
    private CustomerModificationRepository rep;

    @GetMapping("/email")
    public int updateCustomerPersonalEmail(@RequestParam String email, @RequestParam int id) {
        return rep.updateCustomerPersonalEmail(email, id);
    }

    @GetMapping("/username")
    public int updateCustomerName(@RequestParam String username, @RequestParam int id) {
        return rep.updateCustomerUsername(username, id);
    }

    @GetMapping("/pwd")
    public int updateCustomerPwd(@RequestParam String pwd, @RequestParam int id) {
        return rep.updateCustomerPwd(pwd, id);
    }

    @GetMapping("/firstname")
    public int updateCustomerFirstName(@RequestParam String firstname, @RequestParam int id) {
        return rep.updateCustomerFirstName(firstname, id);
    }

    @GetMapping("/lastname")
    public int updateCustomerLastName(@RequestParam String lastname, @RequestParam int id) {
        return rep.updateCustomerLastName(lastname, id);
    }

    @GetMapping("/phonenumber")
    public int updateCustomerPhoneNumber(@RequestParam String phonenumber, @RequestParam int id) {
        return rep.updateCustomerPhoneNumber(phonenumber, id);
    }

    @GetMapping("/primaryaddress")
    public int updateCustomerPrimaryAddress(@RequestParam String address, @RequestParam int id) {
        return rep.updateCustomerPrimaryAddress(address, id);
    }

    @GetMapping("/iconpath")
    public int updateCustomerIconPath(@RequestParam String iconpath, @RequestParam int id) {
        return rep.updateCustomerIconPath(iconpath, id);
    }

    @GetMapping("/bio")
    public int updateCustomerBio(@RequestParam String bio, @RequestParam int id) {
        return rep.updateCustomerBio(bio, id);
    }

    @GetMapping("/socials")
    public int updateCustomerSocialLink(@RequestParam String link, @RequestParam int id, @RequestParam String oldlink) {
        return rep.updateCustomerSocialLink(link, id, oldlink);
    }

}
