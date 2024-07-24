package com.opensell.customer.setting;

import java.util.List;
import com.opensell.service.FileUploadService;
import com.opensell.service.customermodification.ModificationFeedback;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.opensell.model.dto.CustomerModificationData;

/**
 * @author Olivier
 */
@RestController()
@RequestMapping("/api/customer/setting")
@RequiredArgsConstructor
public class SettingController {
    private final SettingService settingService;
    private final FileUploadService fileUploadService;

    @PatchMapping("/change-private-email")
    public ResponseEntity<?> changeCustomerPersonalEmail(@RequestParam int id, @RequestParam String email, @RequestParam String confirmEmail) {
        return settingService.changePersonalEmail(id, email, confirmEmail);
    }

    @PatchMapping("/change-username")
    public ModificationFeedback changeCustomerUsername(@RequestBody CustomerModificationData data) {
        return settingService.changeUsername(data);
    }

    @PatchMapping("/change-pwd")
    public ModificationFeedback changeCustomerPwd(@RequestBody CustomerModificationData data) {
        return settingService.changePwd(data);
    }

    @PatchMapping("/change-first-name")
    public ModificationFeedback changeCustomerFirstName(@RequestBody CustomerModificationData data) {
        return settingService.changeFirstName(data);
    }

    @PatchMapping("/change-last-name")
    public ModificationFeedback changeCustomerLastName(@RequestBody CustomerModificationData data) {
        return settingService.changeLastName(data);
    }

    @PatchMapping("/change-phone-number")
    public ModificationFeedback changeCustomerPhoneNumber(@RequestBody CustomerModificationData data) {
        return settingService.changePhoneNumber(data);
    }

    @PatchMapping("/change-bio")
    public ModificationFeedback changeCustomerBio(@RequestBody CustomerModificationData data) {
        return settingService.changeBio(data);
    }

    @PatchMapping( "/change-public-email")
    public ModificationFeedback changeCustomerExposedEmail(@RequestBody CustomerModificationData data) {
        return settingService.changeExposedEmail(data);
    }

    @PatchMapping("/{id}/icon")
    public ResponseEntity<?> changeCustomerIconPath(@PathVariable int id, @RequestBody MultipartFile iconFile) {
        return settingService.changeIconPath(id, iconFile) ? ResponseEntity.ok("Icon successfully updated.") : ResponseEntity.badRequest().body("Enable to update your icon.");
    }

    @GetMapping("/email/exists")
    public boolean isEmailExists(@RequestParam int id, @RequestParam String email) {
        System.out.println(id);
        System.out.println(email);
        return settingService.isEmailExists(id, email);
    }
}
