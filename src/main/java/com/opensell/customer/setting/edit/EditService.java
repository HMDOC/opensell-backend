package com.opensell.customer.setting.edit;

import com.opensell.model.Customer;
import com.opensell.model.dto.CustomerModificationData;
import com.opensell.model.verification.HtmlCode;
import com.opensell.model.verification.RegexVerifier;
import com.opensell.exception.CustomerModificationException;
import com.opensell.repository.CustomerModificationRepository;
import com.opensell.repository.CustomerRepository;
import com.opensell.service.FileUploadService;
import com.opensell.service.customermodification.*;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.sql.SQLException;
import java.util.List;

/**
 * This service allows to modify the customer's personal information using the repository
 * @author Oliver Mansuy
 */
@Service
@RequiredArgsConstructor
public class EditService {
    private final CustomerModificationRepository rep;
    private final PasswordEncoder passwordEncoder;
    private final CustomerModificationRepository customerModificationRepository;
    private final CustomerRepository customerRepository;
    private final FileUploadService fileUploadService;

    public ResponseEntity<?> changeEmail(int id, String email, String confirmEmail) {
        try {
            boolean areEmailsEqual = email.equals(confirmEmail);

            if(areEmailsEqual && RegexVerifier.EMAIL.verify(email)) {
                Customer customer = customerRepository.findById(id).orElse(null);

                if(customer != null) {
                    customer.setEmail(email);
                    customerModificationRepository.save(customer);
                    return ResponseEntity.ok().build();
                }

                else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found");
            }

            return ResponseEntity.badRequest().body(areEmailsEqual ? "The email and confirm email are not equals" : RegexVerifier.EMAIL.verify(email) ? null : "Invalid email format.");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
        }
    }

    /**
    public record OtherInformations(String username,
                                    String firstName,
                                    String lastName,
                                    String publicEmail,
                                    String bio) {
    }

    public void changeOtherInformations(int id, OtherInformations otherInformations) {
    }*/

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

    public boolean changeIconPath(int id, MultipartFile iconFile) {
        String iconPath = fileUploadService.saveFiles(List.of(iconFile), FileUploadService.FileType.CUSTOMER_PROFILE).getFirst();
        return rep.updateCustomerIconPath(iconPath, id) > 0;
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

    /**
     * Need to put this method inside of Verification.
     */
    public boolean isEmailExists(int id, String email) {
        return customerModificationRepository.isEmailExist(id, email) == 1;
    }
}
