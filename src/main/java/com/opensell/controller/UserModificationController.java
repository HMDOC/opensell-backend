package com.opensell.controller;

import com.opensell.repository.UserModificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Olivier
 */
@RestController
@CrossOrigin("http://localhost:9998")
@RequestMapping("/change")
public class UserModificationController {

    @Autowired
    private UserModificationRepository rep;

    @GetMapping("/email")
    public int updateEmail(@RequestParam String email, @RequestParam int id) {
        return rep.updateUserEmail(email, id);
    }

    @GetMapping("/username")
    public int updateUserName(@RequestParam String username, @RequestParam int id) {
        return rep.updateUserUserName(username, id);
    }

    @GetMapping("/pwd")
    public int updateUserPwd(@RequestParam String pwd, @RequestParam int id) {
        return rep.updateUserPwd(pwd, id);
    }

    @GetMapping("/firstname")
    public int updateUserFirstName(@RequestParam String firstname, @RequestParam int id) {
        return rep.updateUserFirstName(firstname, id);
    }

    @GetMapping("/lastname")
    public int updateUserLastName(@RequestParam String lastname, @RequestParam int id) {
        return rep.updateUserLastName(lastname, id);
    }

    @GetMapping("/phonenumber")
    public int updateUserPhoneNumber(@RequestParam String phonenumber, @RequestParam int id) {
        return rep.updateUserPhoneNumber(phonenumber, id);
    }

    @GetMapping("/primaryaddress")
    public int updateUserPrimaryAddress(@RequestParam String address, @RequestParam int id) {
        return rep.updateUserPrimaryAddress(address, id);
    }

    @GetMapping("/iconpath")
    public int updateUserIconPath(@RequestParam String iconpath, @RequestParam int id) {
        return rep.updateUserIconPath(iconpath, id);
    }

}
