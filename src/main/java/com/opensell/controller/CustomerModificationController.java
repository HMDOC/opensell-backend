package com.opensell.controller;

import com.opensell.entities.dto.CustomerModificationData;
import com.opensell.service.CustomerModificationService;
import com.opensell.service.FileUploadService;
import com.opensell.service.customerModification.ModificationFeedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * @author Olivier
 */
@RestController //extends @RequestBody, which enables automatic serialization of returned values of controllers
@CrossOrigin("${allowedUrl}")
@RequestMapping("/change")
public class CustomerModificationController {

    @Value("${serverUrl}")
    private String serverUrl;

    @Autowired
    private CustomerModificationService service;

    @Autowired
    private FileUploadService fileUploadService;

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

    //@RequestBody List<MultipartFile> multipartFiles
    @PostMapping("/get-image-icon-path")
    public String getImageIconPath(@RequestBody List<MultipartFile> multipartFiles)  {
        try {
            return fileUploadService.saveFiles(multipartFiles, FileUploadService.FileType.CUSTOMER_PROFIL, serverUrl).get(0);
        } catch (Exception e) {
            return null;
        }
    }
}
