package com.opensell.ad.listings;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.opensell.ad.AdRepository;
import com.opensell.ad.catalog.dto.AdPreviewDto;
import com.opensell.ad.listings.dto.AdCreatorDto;
import com.opensell.customer.CustomerRepository;
import com.opensell.enums.FileType;
import com.opensell.exception.AdTitleUniqueException;
import com.opensell.model.Ad;
import com.opensell.repository.*;
import com.opensell.service.FileUploadService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ListingsService {
    private final AdRepository adRepo;
    private final FileUploadService fileUploadService;
    private final CustomerRepository customerRepository;
    private final AdTypeRepository adTypeRepository;

    public AdCreatorDto getAdModificationDto(String idAd) throws JsonProcessingException {
        Ad ad = adRepo.getAdToModify(idAd);
        return ad != null ? AdCreatorDto.fromAd(ad) : null;
    }

    public boolean deleteAd(String idAd) {
        return adRepo.hideAd(idAd) > 0;
    }

    /**
     * Function to verify that a customer, do not have an ad
     * that already have the title in parameter.
     *
     * @param title  The title of the future ad.
     * @param userId The user of the future ad.
     * @param adId   The id of the ad. If it is null, that mean that is being created.
     * @return If the constraint is OK.
     */
    public boolean isTitleConstraintOk(String title, String userId, String adId) {
        // The first ad with the title.
        Ad ad = adRepo.findOneByTitleAndCustomerIdAndDeletedFalse(title, userId).orElse(null);

        // No ad as the title.
        if (ad == null) return true;

        // To verify that the title conflict is not with the same ad(by the id).
        else return ad.getId().equals(adId);
    }

    public List<String> saveImages(List<MultipartFile> images) {
        if (images != null && !images.isEmpty()) {
            List<String> filePaths = fileUploadService.saveFiles(images, FileType.AD_IMAGE);
            if (filePaths == null) throw new RuntimeException("No files was saved.");
            if (filePaths.size() != images.size()) throw new RuntimeException("All images were not saved.");
            else return filePaths;
        }

        else return null;
    }

    public void setFromAdCreator(AdCreatorDto adCreatorDto, Ad ad) {
        if (adCreatorDto.adId() == null) {
            ad.setAddedDate(LocalDateTime.now());
            ad.setCustomer(customerRepository.findOneByIdAndIsDeletedFalseAndIsActivatedTrue(adCreatorDto.customerId()));
        }

        ad.setTitle(adCreatorDto.title());
        ad.setPrice(adCreatorDto.price());
        ad.setAddress(adCreatorDto.address());
        ad.setSold(adCreatorDto.sold());
        ad.setDescription(adCreatorDto.description());
        ad.setTags(adCreatorDto.tags());
        ad.setAdType(adTypeRepository.findOneById(adCreatorDto.adTypeId()));
        ad.setShape(adCreatorDto.shape());
        ad.setVisibility(adCreatorDto.visibility());
        ad.setImages(adCreatorDto.images());
    }

    public ResponseEntity<AdPreviewDto> createOrUpdateAd(@Valid AdCreatorDto adCreatorDto, boolean isUpdate) throws RuntimeException {
        Ad ad = (isUpdate ? adRepo.findOneByIdAndDeletedFalse(adCreatorDto.adId()) : new Ad());

        if (!this.isTitleConstraintOk(adCreatorDto.title(), adCreatorDto.customerId(), ad.getId())) {
            throw new AdTitleUniqueException();
        }

        this.setFromAdCreator(adCreatorDto, ad);

        return new ResponseEntity<>(
            new AdPreviewDto(adRepo.save(ad)),
            HttpStatus.OK
        );
    }

    public List<?> getCustomerAds(String customerId) {
        return customerId != null ? adRepo.getCustomerAds(customerId) : null;
    }
}
