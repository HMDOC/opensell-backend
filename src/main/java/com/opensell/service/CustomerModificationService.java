package com.opensell.service;

import com.opensell.entities.verification.RegexVerifier;
import com.opensell.repository.CustomerModificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import static com.opensell.entities.verification.RegexVerifier.wrongFormatResponse;

/**
 * @author Oliver Mansuy
 */
@Service
public class CustomerModificationService {

    //https://developer.mozilla.org/en-US/docs/Web/HTTP/Status#server_error_responses
    @Autowired
    private CustomerModificationRepository rep;

    public ResponseEntity<Integer> changePersonalEmail(String email, int id) {
        if (RegexVerifier.EMAIL.verify(email)) return getResponse(rep.updateCustomerPersonalEmail(email, id));
        return wrongFormatResponse();
    }

    public ResponseEntity<Integer> changeExposedEmail(String email, int id) {
        if (RegexVerifier.EMAIL.verify(email)) return getResponse(rep.updateCustomerExposedEmail(email, id));
        return wrongFormatResponse();
    }

    public ResponseEntity<Integer> changeFirstName(String firstName, int id) {
        if (RegexVerifier.FIRST_LAST_NAME.verify(firstName)) return getResponse(rep.updateCustomerFirstName(firstName, id));
        return wrongFormatResponse();
    }

    public ResponseEntity<Integer> changeLastName(String lastName, int id) {
        if (RegexVerifier.FIRST_LAST_NAME.verify(lastName)) return getResponse(rep.updateCustomerLastName(lastName, id));
        return wrongFormatResponse();
    }

    public ResponseEntity<Integer> changePwd(String pwd, int id) {
        if (RegexVerifier.PWD.verify(pwd)) return getResponse(rep.updateCustomerPwd(pwd, id));
        return wrongFormatResponse();
    }

    public ResponseEntity<Integer> changeUsername(String username, int id) {
        if (RegexVerifier.USERNAME.verify(username)) return getResponse(rep.updateCustomerUsername(username, id));
        return wrongFormatResponse();
    }

    public ResponseEntity<Integer> changePhoneNumber(String phoneNumber, int id) {
        if (RegexVerifier.PHONE_NUMBER.verify(phoneNumber)) return getResponse(rep.updateCustomerPhoneNumber(phoneNumber, id));
        return wrongFormatResponse();
    }

    public ResponseEntity<Integer> changePrimaryAddress(String address, int id) {
        return getResponse(rep.updateCustomerPrimaryAddress(address, id));
    }

    public ResponseEntity<Integer> changeIconPath(String iconPath, int id) {
        return getResponse(rep.updateCustomerIconPath(iconPath, id));
    }

    public ResponseEntity<Integer> changeBio(@RequestParam String bio, @RequestParam int id) {
        return getResponse(rep.updateCustomerBio(bio, id));
    }

    public ResponseEntity<Integer> changeSocialLink(@RequestParam String link, @RequestParam int id, @RequestParam String oldLink) {
        return getResponse(rep.updateCustomerSocialLink(link, id, oldLink));
    }

    private static ResponseEntity<Integer> getResponse(int body) {
        if (body == 0) return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
        else if (body == 1) return new ResponseEntity<>(body, HttpStatus.OK);
        else return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }



}
