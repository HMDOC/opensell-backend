package com.opensell.customer.setting.edit;

import com.opensell.service.customermodification.ModificationFeedback;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.opensell.model.dto.CustomerModificationData;

/**
 * @author Olivier
 */
@RestController
@RequestMapping("/api/customer/setting/edit")
@RequiredArgsConstructor
public class EditController {
    private final EditService editService;

    @PatchMapping("/private-email")
    public ResponseEntity<?> changeCustomerPersonalEmail(@RequestParam int id, @RequestParam String email, @RequestParam String confirmEmail) {
        return editService.changePersonalEmail(id, email, confirmEmail);
    }

    @PatchMapping("/username")
    public ModificationFeedback changeCustomerUsername(@RequestBody CustomerModificationData data) {
        return editService.changeUsername(data);
    }

    @PatchMapping("/pwd")
    public ModificationFeedback changeCustomerPwd(@RequestBody CustomerModificationData data) {
        return editService.changePwd(data);
    }

    @PatchMapping("/first-name")
    public ModificationFeedback changeCustomerFirstName(@RequestBody CustomerModificationData data) {
        return editService.changeFirstName(data);
    }

    @PatchMapping("/last-name")
    public ModificationFeedback changeCustomerLastName(@RequestBody CustomerModificationData data) {
        return editService.changeLastName(data);
    }

    @PatchMapping("/phone-number")
    public ModificationFeedback changeCustomerPhoneNumber(@RequestBody CustomerModificationData data) {
        return editService.changePhoneNumber(data);
    }

    @PatchMapping("/bio")
    public ModificationFeedback changeCustomerBio(@RequestBody CustomerModificationData data) {
        return editService.changeBio(data);
    }

    @PatchMapping( "/public-email")
    public ModificationFeedback changeCustomerExposedEmail(@RequestBody CustomerModificationData data) {
        return editService.changeExposedEmail(data);
    }

    @PatchMapping("/{id}/icon")
    public ResponseEntity<?> changeCustomerIconPath(@PathVariable int id, @RequestBody MultipartFile iconFile) {
        return editService.changeIconPath(id, iconFile) ? ResponseEntity.ok("Icon successfully updated.") : ResponseEntity.badRequest().body("Enable to update your icon.");
    }

    @GetMapping("/email/exists")
    public boolean isEmailExists(@RequestParam int id, @RequestParam String email) {
        System.out.println(id);
        System.out.println(email);
        return editService.isEmailExists(id, email);
    }
}
