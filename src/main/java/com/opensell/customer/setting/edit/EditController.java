package com.opensell.customer.setting.edit;

import com.opensell.customer.setting.edit.dto.OtherInformationDto;
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

    @PatchMapping("/pwd")
    public ModificationFeedback changePwd(@RequestBody CustomerModificationData data) {
        return editService.changePwd(data);
    }

    @PatchMapping("/{id}/other-information")
    public ResponseEntity<?> changeOtherInformation(@PathVariable int id, OtherInformationDto otherInformationDto) {
        return editService.changeOtherInformation(id, otherInformationDto);
    }

    @PatchMapping("/{id}/icon")
    public ResponseEntity<?> changeIconPath(@PathVariable int id, @RequestBody MultipartFile iconFile) {
        return editService.changeIconPath(id, iconFile) ? ResponseEntity.ok("Icon successfully updated.") : ResponseEntity.badRequest().body("Enable to update your icon.");
    }
}
