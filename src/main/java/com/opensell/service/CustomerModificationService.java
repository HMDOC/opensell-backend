package com.opensell.service;

import com.opensell.entities.verification.RegexVerifier;
import com.opensell.repository.CustomerModificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import static com.opensell.entities.verification.RegexVerifier.wrongFormatResponse;

/**
 * This service allows to modify the customer's personal information using the repository
 * @author Oliver Mansuy
 */
@Service
public class CustomerModificationService {

    //https://developer.mozilla.org/en-US/docs/Web/HTTP/Status#server_error_responses
    @Autowired
    private CustomerModificationRepository rep;

    public ResponseEntity<Integer> changePersonalEmail(String email, String link) {
        if (RegexVerifier.EMAIL.verify(email)) return getResponse(rep.updateCustomerPersonalEmail(email, link));
        return wrongFormatResponse();
    }

    public ResponseEntity<Integer> changeExposedEmail(String email, String link) {
        if (RegexVerifier.EMAIL.verify(email)) return getResponse(rep.updateCustomerExposedEmail(email, link));
        return wrongFormatResponse();
    }

    public ResponseEntity<Integer> changeFirstName(String firstName, String link) {
        if (RegexVerifier.FIRST_LAST_NAME.verify(firstName)) return getResponse(rep.updateCustomerFirstName(firstName, link));
        return wrongFormatResponse();
    }

    public ResponseEntity<Integer> changeLastName(String lastName, String link) {
        if (RegexVerifier.FIRST_LAST_NAME.verify(lastName)) return getResponse(rep.updateCustomerLastName(lastName, link));
        return wrongFormatResponse();
    }

    public ResponseEntity<Integer> changePwd(String pwd, String link) {
        if (RegexVerifier.PWD.verify(pwd)) return getResponse(rep.updateCustomerPwd(pwd, link));
        return wrongFormatResponse();
    }

    public ResponseEntity<Integer> changeUsername(String username, String link) {
        if (RegexVerifier.USERNAME.verify(username)) return getResponse(rep.updateCustomerUsername(username, link));
        return wrongFormatResponse();
    }

    public ResponseEntity<Integer> changePhoneNumber(String phoneNumber, String link) {
        if (RegexVerifier.PHONE_NUMBER.verify(phoneNumber)) return getResponse(rep.updateCustomerPhoneNumber(phoneNumber, link));
        return wrongFormatResponse();
    }

    public ResponseEntity<Integer> changePrimaryAddress(String address, String link) {
        return getResponse(rep.updateCustomerPrimaryAddress(address, link));
    }

    public ResponseEntity<Integer> changeIconPath(String iconPath, String link) {
        return getResponse(rep.updateCustomerIconPath(iconPath, link));
    }

    public ResponseEntity<Integer> changeBio(String bio,String link) {
        return getResponse(rep.updateCustomerBio(bio, link));
    }

    public ResponseEntity<Integer> changeSocialLink(String link, String customer_link, String oldLink) {
        return getResponse(rep.updateCustomerSocialLink(link, customer_link, oldLink));
    }

    private static ResponseEntity<Integer> getResponse(int body) {
        if (body == 0) return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
        else if (body == 1) return new ResponseEntity<>(body, HttpStatus.OK);
        else return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
