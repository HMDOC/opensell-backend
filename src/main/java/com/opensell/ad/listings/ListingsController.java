package com.opensell.ad.listings;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.opensell.ad.catalog.dto.AdPreviewDto;
import com.opensell.ad.listings.dto.AdCreatorDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RequestMapping("/api/ad/listings")
@RestController
@RequiredArgsConstructor
@Validated
public class ListingsController {
    private final ListingsService listingsService;

    /**
     * To get an adView when a user want to modify an ad.
     */
    @GetMapping("/{idAd}")
    public AdCreatorDto getAdModificationDto(@PathVariable String idAd) throws JsonProcessingException {
        return listingsService.getAdModificationDto(idAd);
    }

    @DeleteMapping("/{idAd}")
    public boolean deleteAd(@PathVariable String idAd) {
        return listingsService.deleteAd(idAd);
    }

    @PostMapping("/images")
    public ResponseEntity<List<String>> saveImages(@RequestBody List<MultipartFile> images) {
        return ResponseEntity.ok(listingsService.saveImages(images));
    }

    @PostMapping
    public ResponseEntity<AdPreviewDto> createAd(@RequestBody AdCreatorDto adCreatorDto) {
        return listingsService.createOrUpdateAd(adCreatorDto, false);
    }

    @PatchMapping
    public ResponseEntity<AdPreviewDto> updateAd(@RequestBody AdCreatorDto adCreatorDto) {
        return listingsService.createOrUpdateAd(adCreatorDto, true);
    }

    @GetMapping("/is-title-constraint-ok")
    public boolean isTitleConstraintOk(@RequestParam String title, @RequestParam String customerId, @RequestParam(required = false) String adId) {
        return listingsService.isTitleConstraintOk(title, customerId, adId);
    }

    // CustomerId will be removed with Spring security
    @GetMapping("/{customerId}/all")
    public List<?> getCustomerAds(@PathVariable String customerId) {
        return listingsService.getCustomerAds(customerId);
    }
}
