package com.opensell.service;

import com.opensell.entities.dto.CustomerModificationData;
import com.opensell.entities.verification.HtmlCode;
import com.opensell.entities.verification.RegexVerifier;
import com.opensell.repository.CustomerModificationRepository;
import com.opensell.service.customerModification.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.SQLException;


/**
 * This service allows to modify the customer's personal information using the repository
 * @author Oliver Mansuy
 */
@Service
public class CustomerModificationService {


    @Autowired
    private CustomerModificationRepository rep;

    public ModificationFeedback changePersonalEmail(CustomerModificationData data) {
        return getFeedback(() -> rep.updateCustomerPersonalEmail(data.value(), data.id()), () -> RegexVerifier.EMAIL.verify(data.value()), data.value());
    }

    public ModificationFeedback changeExposedEmail(CustomerModificationData data) {
        return getFeedback(() -> rep.updateCustomerExposedEmail(data.value(), data.id()), () -> RegexVerifier.EMAIL.verify(data.value()), data.value());
    }

    public ModificationFeedback changeFirstName(CustomerModificationData data) {
        return getFeedback(() -> rep.updateCustomerFirstName(data.value(), data.id()), () -> RegexVerifier.FIRST_LAST_NAME.verify(data.value()), data.value());
    }

    public ModificationFeedback changeLastName(CustomerModificationData data) {
        return getFeedback(() -> rep.updateCustomerLastName(data.value(), data.id()), () -> RegexVerifier.FIRST_LAST_NAME.verify(data.value()), data.value());
    }

    public ModificationFeedback changePwd(CustomerModificationData data) {
        return getFeedback(() -> rep.updateCustomerPwd(data.value(), data.id()), () -> RegexVerifier.PWD.verify(data.value()), data.value());
    }

    public ModificationFeedback changeUsername(CustomerModificationData data) {
        return getFeedback(() -> rep.updateCustomerUsername(data.value(), data.id()), () -> RegexVerifier.USERNAME.verify(data.value()), data.value());
    }

    public ModificationFeedback changePhoneNumber(CustomerModificationData data) {
        return getFeedback(() -> rep.updateCustomerPhoneNumber(data.value(), data.id()), () -> RegexVerifier.PHONE_NUMBER.verify(data.value()), data.value());
    }

    public ModificationFeedback changePrimaryAddress(CustomerModificationData data) {
        return getFeedback(() -> rep.updateCustomerPrimaryAddress(data.value(), data.id()), () -> true, data.value());
    }

    public ModificationFeedback changeIconPath(CustomerModificationData data) {
        return getFeedback(() -> rep.updateCustomerIconPath(data.value(), data.id()), () -> true, data.value());
    }

    public ModificationFeedback changeBio(CustomerModificationData data) {
        return getFeedback(() -> rep.updateCustomerBio(data.value(), data.id()), () -> true, data.value());
    }
    //
    public ModificationFeedback changeSocials1(CustomerModificationData data) {
        return getFeedback(() -> rep.updateCustomerSocials1(data.value(), data.id()), () -> true, data.value());
    }
    //
    public ModificationFeedback changeSocials2(CustomerModificationData data) {
        return getFeedback(() -> rep.updateCustomerSocials2(data.value(), data.id()), () -> true, data.value());
    }
    //
    public ModificationFeedback changeSocials3(CustomerModificationData data) {
        return getFeedback(() -> rep.updateCustomerSocials3(data.value(), data.id()), () -> true, data.value());
    }
    //
    public ModificationFeedback changeSocials4(CustomerModificationData data) {
        return getFeedback(() -> rep.updateCustomerSocials4(data.value(), data.id()), () -> true, data.value());
    }
    //
    public ModificationFeedback changeSocials5(CustomerModificationData data) {
        return getFeedback(() -> rep.updateCustomerSocials5(data.value(), data.id()), () -> true, data.value());
    }

    private ModificationFeedback getFeedback(UpdateCallable callback, ValueValidationCallable validation, String value) {
        int result = 0;
        try {
            if (!validation.isValid()) throw CustomerModificationException.formattingException();
            result = callback.updateStatement();
            if (result == 0) throw new SQLException(); //needs a new custom Exception
            return new ModificationFeedback(HtmlCode.SUCCESS, result, value);
        } catch (CustomerModificationException cException) {
            return new ModificationFeedback(HtmlCode.WRONG_FORMAT, result, value);
        } catch (SQLException sqlException) {
            return new ModificationFeedback(HtmlCode.SQL_ERROR, result, value);
        } catch (Exception exception) {
            return new ModificationFeedback(HtmlCode.OTHER_ERROR, result, value);
        }
    }
}
