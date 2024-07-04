package com.opensell.service;

import com.opensell.model.dto.CustomerModificationData;
import com.opensell.model.verification.HtmlCode;
import com.opensell.model.verification.RegexVerifier;
import com.opensell.exception.CustomerModificationException;
import com.opensell.repository.CustomerModificationRepository;
import com.opensell.service.customerModification.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLException;


/**
 * This service allows to modify the customer's personal information using the repository
 * @author Oliver Mansuy
 */
@Service
@RequiredArgsConstructor
public class CustomerModificationService {
    private final CustomerModificationRepository rep;
    private final PasswordEncoder passwordEncoder;

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
        return getFeedback(() -> rep.updateCustomerPwd(passwordEncoder.encode(data.value()), data.id()), () -> RegexVerifier.PWD.verify(data.value()), data.value());
    }

    public ModificationFeedback changeUsername(CustomerModificationData data) {
        return getFeedback(() -> rep.updateCustomerUsername(data.value(), data.id()), () -> RegexVerifier.USERNAME.verify(data.value()), data.value());
    }

    public ModificationFeedback changePhoneNumber(CustomerModificationData data) {
        return getFeedback(() -> rep.updateCustomerPhoneNumber(data.value(), data.id()), () -> RegexVerifier.PHONE_NUMBER.verify(data.value()), data.value());
    }

    public ModificationFeedback changeIconPath(CustomerModificationData data) {
        return getFeedback(() -> rep.updateCustomerIconPath(data.value(), data.id()), () -> true, data.value());
    }

    public ModificationFeedback changeBio(CustomerModificationData data) {
        return getFeedback(() -> rep.updateCustomerBio(data.value(), data.id()), () -> true, data.value());
    }

    private ModificationFeedback getFeedback(UpdateCallable callback, ValueValidationCallable validation, String value) {
        int result = 0;
        try {
            if (!validation.isValid()) throw CustomerModificationException.formattingException();
            result = callback.updateStatement();
            if (result == 0) throw new SQLException();
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
