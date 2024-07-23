package com.opensell.ad.modification;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.opensell.model.Ad;
import com.opensell.model.dto.AdCreator;
import com.opensell.model.dto.DisplayAdView;
import com.opensell.repository.AdRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RequestMapping("/api/ad/modification")
@RestController
@RequiredArgsConstructor
public class AdModificationController {
    private final AdRepository adRepo;
    private final AdModificationService adModificationService;

    /**
     * To get an adView when a user want to modify an ad.
     */
    @GetMapping("/{idAd}")
    public AdCreator getAdModifyView(@PathVariable int idAd) throws JsonProcessingException {
        Ad ad = adRepo.getAdToModify(idAd);
        return ad != null ? AdCreator.fromAd(ad) : null ;
    }

    @DeleteMapping("/{idAd}")
    public boolean deleteAd(@PathVariable int idAd) {
        return adRepo.hideAd(idAd) > 0;
    }

    @PostMapping
    public ResponseEntity<DisplayAdView> createAd(@RequestBody(required = false) List<MultipartFile> images,
                                                  @RequestParam(required = false) List<Integer> imagePositions,
                                                  AdCreator adCreator) throws JsonProcessingException {
        return adModificationService.createOrUpdateAd(images, imagePositions, adCreator, false);
    }

    @PatchMapping
    public ResponseEntity<DisplayAdView> updateAd(@RequestBody(required = false) List<MultipartFile> images,
                                                  @RequestParam(required = false) List<Integer> imagePositions,
                                                  AdCreator adCreator) throws JsonProcessingException {
        return adModificationService.createOrUpdateAd(images, imagePositions, adCreator, true);
    }

    @GetMapping("/is-title-constraint-ok")
    public boolean isTitleConstraintOk(@RequestParam String title, @RequestParam int customerId, @RequestParam(required = false) Integer adId) {
        return adModificationService.isTitleConstraintOk(title, customerId, adId);
    }
}
