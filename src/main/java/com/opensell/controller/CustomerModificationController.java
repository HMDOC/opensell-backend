package com.opensell.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.opensell.model.dto.CustomerModificationData;
import com.opensell.service.CustomerModificationService;
import com.opensell.service.FileUploadService;
import com.opensell.service.customerModification.ModificationFeedback;

/**
 * @author Olivier
 */
@RestController //extends @RequestBody, which enables automatic serialization of returned values of controllers
@CrossOrigin("${allowedUrl}")
@RequestMapping("/change")
@RequiredArgsConstructor
public class CustomerModificationController {
    private final CustomerModificationService service;
    private final FileUploadService fileUploadService;

    @PutMapping("/change-private-email")
    public ModificationFeedback changeCustomerPersonalEmail(@RequestBody CustomerModificationData data) {
        return service.changePersonalEmail(data);
    }

    @PutMapping("/change-username")
    public ModificationFeedback changeCustomerUsername(@RequestBody CustomerModificationData data) {
        return service.changeUsername(data);
    }

    @PutMapping("/change-pwd")
    public ModificationFeedback changeCustomerPwd(@RequestBody CustomerModificationData data) {
        return service.changePwd(data);
    }

    @PutMapping("/change-first-name")
    public ModificationFeedback changeCustomerFirstName(@RequestBody CustomerModificationData data) {
        return service.changeFirstName(data);
    }

    @PutMapping("/change-last-name")
    public ModificationFeedback changeCustomerLastName(@RequestBody CustomerModificationData data) {
        return service.changeLastName(data);
    }

    @PutMapping("/change-phone-number")
    public ModificationFeedback changeCustomerPhoneNumber(@RequestBody CustomerModificationData data) {
        return service.changePhoneNumber(data);
    }

    @PutMapping("/change-icon-path")
    public ModificationFeedback changeCustomerIconPath(@RequestBody CustomerModificationData data) {
        return service.changeIconPath(data);
    }

    @PutMapping("/change-bio")
    public ModificationFeedback changeCustomerBio(@RequestBody CustomerModificationData data) {
        return service.changeBio(data);
    }

    @PutMapping( "/change-public-email")
    public ModificationFeedback changeCustomerExposedEmail(@RequestBody CustomerModificationData data) {
        return service.changeExposedEmail(data);
    }

    @PostMapping("/get-image-icon-path")
    public String getImageIconPath(@RequestBody List<MultipartFile> multipartFiles)  {
        try {
            return fileUploadService.saveFiles(multipartFiles, FileUploadService.FileType.CUSTOMER_PROFIL).get(0);
        } catch (Exception e) {
            return null;
        }
    }
}
