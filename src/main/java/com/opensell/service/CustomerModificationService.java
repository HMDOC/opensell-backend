package com.opensell.service;

import com.opensell.entities.verification.RegexVerifier;
import com.opensell.repository.CustomerModificationRepository;
import com.opensell.service.customerModification.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;


/**
 * This service allows to modify the customer's personal information using the repository
 * @author Oliver Mansuy
 */
@Service
public class CustomerModificationService {


    @Autowired
    private CustomerModificationRepository rep;

    public ModificationFeedback changePersonalEmail(String email, String link) {
        return getFeedback(() -> rep.updateCustomerPersonalEmail(email, link), () -> RegexVerifier.EMAIL.verify(email), email);
    }

    public ModificationFeedback changeExposedEmail(String email, String link) {
        return getFeedback(() -> rep.updateCustomerExposedEmail(email, link), () -> RegexVerifier.EMAIL.verify(email), email);
    }

    public ModificationFeedback changeFirstName(String firstName, String link) {
        return getFeedback(() -> rep.updateCustomerFirstName(firstName, link), () -> RegexVerifier.FIRST_LAST_NAME.verify(firstName), firstName);
    }

    public ModificationFeedback changeLastName(String lastName, String link) {
        return getFeedback(() -> rep.updateCustomerLastName(lastName, link), () -> RegexVerifier.FIRST_LAST_NAME.verify(lastName), lastName);
    }

    public ModificationFeedback changePwd(String pwd, String link) {
        return getFeedback(() -> rep.updateCustomerPwd(pwd, link), () -> RegexVerifier.PWD.verify(pwd), pwd);
    }

    public ModificationFeedback changeUsername(String username, String link) {
        return getFeedback(() -> rep.updateCustomerUsername(username, link), () -> RegexVerifier.USERNAME.verify(username), username);
    }

    public ModificationFeedback changePhoneNumber(String phoneNumber, String link) {
        return getFeedback(() -> rep.updateCustomerPhoneNumber(phoneNumber, link), () -> RegexVerifier.PHONE_NUMBER.verify(phoneNumber), phoneNumber);
    }

    public ModificationFeedback changePrimaryAddress(String address, String link) {
        return getFeedback(() -> rep.updateCustomerPrimaryAddress(address, link), () -> true, address);
    }

    public ModificationFeedback changeIconPath(String iconPath, String link) {
        return getFeedback(() -> rep.updateCustomerIconPath(iconPath, link), () -> true, iconPath);
    }

    public ModificationFeedback changeBio(String bio,String link) {
        return getFeedback(() -> rep.updateCustomerBio(bio, link), () -> true, bio);
    }

    public ModificationFeedback changeSocialLink(String link, String customer_link, String oldLink) {
        return getFeedback(() -> rep.updateCustomerSocialLink(link, customer_link, oldLink), () -> true, link);
    }

    private ModificationFeedback getFeedback(UpdateCallable callback, ValueValidationCallable validation, String value) {
        int result = 0;
        try {
            if (!validation.isValid()) throw CustomerModificationException.formattingException();
            result = callback.updateStatement();
            if (result == 0) throw new SQLException(); //needs a new custom Exception
            return new ModificationFeedback(CustomerModificationCode.OK, result, value);
        } catch (CustomerModificationException cException) {
            return new ModificationFeedback(CustomerModificationCode.WRONG_FORMAT, result, value);
        } catch (SQLException sqlException) {
            return new ModificationFeedback(CustomerModificationCode.SQL_ERROR, result, value);
        } catch (Exception exception) {
            return new ModificationFeedback(CustomerModificationCode.OTHER_ERROR, result, value);
        }
    }
}
