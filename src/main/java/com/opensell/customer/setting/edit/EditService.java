package com.opensell.customer.setting.edit;

import com.opensell.customer.setting.edit.dto.OtherInformationDto;
import com.opensell.customer.setting.edit.dto.PasswordDto;
import com.opensell.enums.FileType;
import com.opensell.exception.CustomerNotFound;
import com.opensell.model.Customer;
import com.opensell.enums.RegexVerifier;
import com.opensell.customer.setting.SettingRepository;
import com.opensell.customer.CustomerRepository;
import com.opensell.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

/**
 * This service allows to modify the customer's personal information using the repository
 * @author Oliver Mansuy
 */
@Service
@RequiredArgsConstructor
public class EditService {
    private final SettingRepository rep;
    private final PasswordEncoder passwordEncoder;
    private final SettingRepository settingRepository;
    private final CustomerRepository customerRepository;
    private final FileUploadService fileUploadService;

    public ResponseEntity<?> changeEmail(int id, String email, String confirmEmail) {
        try {
            boolean areEmailsEqual = email.equals(confirmEmail);

            if(areEmailsEqual && RegexVerifier.EMAIL.verify(email)) {
                Customer customer = customerRepository.findById(id).orElse(null);

                if(customer != null) {
                    customer.setEmail(email);
                    settingRepository.save(customer);
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
            Customer customer = settingRepository.findById(id).orElseThrow(CustomerNotFound::new);

            if(!passwordEncoder.matches(passwordDto.oldPassword(), customer.getPwd())) {
                return ResponseEntity.badRequest().body(PasswordError.INVALID_OLD_PASSWORD);
            }

            customer.setPwd(passwordEncoder.encode(passwordDto.password()));
            settingRepository.save(customer);
            return ResponseEntity.ok().body("Password changed successfully.");
        } catch (CustomerNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found");
        }
    }


    public boolean changeIconPath(int id, MultipartFile iconFile) {
        String fileName = iconFile != null ? fileUploadService.saveFiles(List.of(iconFile), FileType.CUSTOMER_PROFILE).getFirst() : null;

        return rep.updateCustomerIconPath(fileName, id) > 0;
    }

    /**
     * To return null if the content is blank.
     */
    public String getContentOrNull(String content) {
        return content == null ? null : (content.isBlank() ? null : content);
    }

    public ResponseEntity<?> changeOtherInformation(int id, OtherInformationDto otherInformationDto) {
        try {
            Customer customer = settingRepository.findById(id).orElseThrow(CustomerNotFound::new);

            customer.setUsername(otherInformationDto.username());
            customer.setFirstName(getContentOrNull(otherInformationDto.firstName()));
            customer.setLastName(getContentOrNull(otherInformationDto.lastName()));
            customer.setBio(getContentOrNull(otherInformationDto.bio()));

            settingRepository.save(customer);
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
}
