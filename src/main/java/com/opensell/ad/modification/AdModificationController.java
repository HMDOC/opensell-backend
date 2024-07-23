package com.opensell.ad.modification;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.opensell.ad.modification.dto.AdCreatorDto;
import com.opensell.model.dto.DisplayAdView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RequestMapping("/api/ad/modification")
@RestController
@RequiredArgsConstructor
public class AdModificationController {
    private final AdModificationService adModificationService;

    /**
     * To get an adView when a user want to modify an ad.
     */
    @GetMapping("/{idAd}")
    public AdCreatorDto getAdModificationDto(@PathVariable int idAd) throws JsonProcessingException {
        return adModificationService.getAdModificationDto(idAd);
    }

    @DeleteMapping("/{idAd}")
    public boolean deleteAd(@PathVariable int idAd) {
        return adModificationService.deleteAd(idAd);
    }

    @PostMapping
    public ResponseEntity<DisplayAdView> createAd(@RequestBody(required = false) List<MultipartFile> images,
                                                  @RequestParam(required = false) List<Integer> imagePositions,
                                                  AdCreatorDto adCreatorDto) throws JsonProcessingException {
        return adModificationService.createOrUpdateAd(images, imagePositions, adCreatorDto, false);
    }

    @PatchMapping
    public ResponseEntity<DisplayAdView> updateAd(@RequestBody(required = false) List<MultipartFile> images,
                                                  @RequestParam(required = false) List<Integer> imagePositions,
                                                  AdCreatorDto adCreatorDto) throws JsonProcessingException {
        return adModificationService.createOrUpdateAd(images, imagePositions, adCreatorDto, true);
    }

    @GetMapping("/is-title-constraint-ok")
    public boolean isTitleConstraintOk(@RequestParam String title, @RequestParam int customerId, @RequestParam(required = false) Integer adId) {
        return adModificationService.isTitleConstraintOk(title, customerId, adId);
    }
}
