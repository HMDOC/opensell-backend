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

    @PatchMapping("/email")
    public ResponseEntity<?> changeEmail(@RequestParam int id, @RequestParam String email, @RequestParam String confirmEmail) {
        return editService.changeEmail(id, email, confirmEmail);
    }

    @PatchMapping("/username")
    public ModificationFeedback changeUsername(@RequestBody CustomerModificationData data) {
        return editService.changeUsername(data);
    }

    @PatchMapping("/pwd")
    public ModificationFeedback changePwd(@RequestBody CustomerModificationData data) {
        return editService.changePwd(data);
    }

    @PatchMapping("/first-name")
    public ModificationFeedback changeFirstName(@RequestBody CustomerModificationData data) {
        return editService.changeFirstName(data);
    }

    @PatchMapping("/last-name")
    public ModificationFeedback changeLastName(@RequestBody CustomerModificationData data) {
        return editService.changeLastName(data);
    }

    @PatchMapping("/phone-number")
    public ModificationFeedback changePhoneNumber(@RequestBody CustomerModificationData data) {
        return editService.changePhoneNumber(data);
    }

    @PatchMapping("/bio")
    public ModificationFeedback changeBio(@RequestBody CustomerModificationData data) {
        return editService.changeBio(data);
    }

    @PatchMapping("/{id}/icon")
    public ResponseEntity<?> changeIconPath(@PathVariable int id, @RequestBody MultipartFile iconFile) {
        return editService.changeIconPath(id, iconFile) ? ResponseEntity.ok("Icon successfully updated.") : ResponseEntity.badRequest().body("Enable to update your icon.");
    }

    @GetMapping("/email/exists")
    public boolean isEmailExists(@RequestParam int id, @RequestParam String email) {
        System.out.println(id);
        System.out.println(email);
        return editService.isEmailExists(id, email);
    }
}
