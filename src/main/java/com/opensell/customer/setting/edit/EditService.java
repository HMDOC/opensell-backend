package com.opensell.customer.setting.edit;

import com.opensell.customer.setting.edit.dto.OtherInformationDto;
import com.opensell.customer.setting.edit.dto.PasswordDto;
import com.opensell.exception.CustomerNotFound;
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
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
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

    public interface PasswordError {
        int PASSWORD_NOT_EQUAL_CONFIRM_PASSWORD = 333;
        int INVALID_OLD_PASSWORD = 102;
    }

    public ResponseEntity<?> changePassword(int id, PasswordDto passwordDto) {
        if(!passwordDto.password().equals(passwordDto.confirmPassword())) {
            return ResponseEntity.badRequest().body(PasswordError.PASSWORD_NOT_EQUAL_CONFIRM_PASSWORD);
        }

        // To do not make a useless query
        if(passwordDto.password().equals(passwordDto.oldPassword())) {
            return ResponseEntity.ok().body("No change were made because old password is the same as the new one.");
        }

        // To see : RegexVerifier.PWD.verify
        try {
            Customer customer = customerModificationRepository.findById(id).orElseThrow(CustomerNotFound::new);

            if(!passwordEncoder.matches(passwordDto.oldPassword(), customer.getPwd())) {
                return ResponseEntity.badRequest().body(PasswordError.INVALID_OLD_PASSWORD);
            }

            customer.setPwd(passwordEncoder.encode(passwordDto.password()));
            customerModificationRepository.save(customer);
            return ResponseEntity.ok().body("Password changed successfully.");
        } catch (CustomerNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found");
        }
    }


    public boolean changeIconPath(int id, MultipartFile iconFile) {
        String iconPath = fileUploadService.saveFiles(List.of(iconFile), FileUploadService.FileType.CUSTOMER_PROFILE).getFirst();
        return rep.updateCustomerIconPath(iconPath, id) > 0;
    }

    public ResponseEntity<?> changeOtherInformation(int id, OtherInformationDto otherInformationDto) {
        try {
            Customer customer = customerModificationRepository.findById(id).orElseThrow(CustomerNotFound::new);

            customer.getCustomerInfo().setBio(otherInformationDto.bio());
            customer.getCustomerInfo().setFirstName(otherInformationDto.firstName());
            customer.getCustomerInfo().setLastName(otherInformationDto.lastName());
            customer.getCustomerInfo().setLastName(otherInformationDto.lastName());
            customer.setUsername(otherInformationDto.username());

            customerModificationRepository.save(customer);
            return ResponseEntity.ok("Customer has been updated.");
        } catch (Exception e) {
            if(e instanceof CustomerNotFound) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found.");
            }
            if(e instanceof DuplicateKeyException) {
                return ResponseEntity.badRequest().body("Username already exists");
            } else {
                return ResponseEntity.internalServerError().body("Server error");
            }
        }
    }

    @Deprecated(forRemoval = true)
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
