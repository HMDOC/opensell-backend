package com.opensell.customer.setting.edit;

import com.opensell.customer.setting.edit.dto.OtherInformationDto;
import com.opensell.customer.setting.edit.dto.PasswordDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Olivier
 */
@RestController
@RequestMapping("/api/customer/setting/edit")
@RequiredArgsConstructor
public class EditController {
    private final EditService editService;

    @PatchMapping("/{id}/email")
    public ResponseEntity<?> changeEmail(@PathVariable String id, @RequestParam String email, @RequestParam String confirmEmail) {
        return editService.changeEmail(id, email, confirmEmail);
    }

    @PatchMapping("/{id}/password")
    public ResponseEntity<?> changePassword(@PathVariable String id, PasswordDto passwordDto) {
        return editService.changePassword(id, passwordDto);
    }

    @PatchMapping("/{id}/other-information")
    public ResponseEntity<?> changeOtherInformation(@PathVariable String id, OtherInformationDto otherInformationDto) {
        return editService.changeOtherInformation(id, otherInformationDto);
    }

    @PatchMapping("/{id}/icon")
    public ResponseEntity<?> changeIconPath(@PathVariable String id, @RequestBody(required = false) MultipartFile iconFile) {
        return editService.changeIconPath(id, iconFile) ? ResponseEntity.ok("Icon successfully updated.") : ResponseEntity.badRequest().body("Enable to update your icon.");
    }
}
