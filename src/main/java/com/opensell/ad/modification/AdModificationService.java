package com.opensell.ad.modification;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opensell.exception.AdTitleUniqueException;
import com.opensell.model.Ad;
import com.opensell.model.ad.AdImage;
import com.opensell.model.ad.AdTag;
import com.opensell.model.dto.AdCreator;
import com.opensell.model.dto.DisplayAdView;
import com.opensell.repository.*;
import com.opensell.service.FileUploadService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AdModificationService {
    private final AdRepository adRepo;
    private final FileUploadService fileUploadService;
    private final AdImageRepository adImageRepository;
    private final CustomerRepository customerRepository;
    private final AdTypeRepository adTypeRepository;
    private final AdTagRepository adTagRepo;

    public Set<AdTag> getAdTagsFromStringSet(Set<String> tags) {
        Set<AdTag> adTags = new LinkedHashSet<>();

        // Map over the set of string
        tags.forEach(tag -> {
            if (AdTag.isNameValid(tag)) {
                // Get the old tag from the database
                AdTag tagTemp = adTagRepo.findOneByName(tag);

                // If the tag already exists
                if (tagTemp != null) adTags.add(tagTemp);

                    // If the tag does exists
                else adTags.add(adTagRepo.save(new AdTag(tag)));
            }
        });

        return adTags;
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
    public boolean isTitleConstraintOk(String title, int userId, Integer adId) {
        // The first ad with the title.
        Ad ad = adRepo.findOneByTitleAndCustomerIdCustomerAndIsDeletedFalse(title, userId).orElse(null);

        // No ad as the title.
        if (ad == null) return true;

            // To verify that the title conflict is not with the same ad(by the id).
        else return adId != null && ad.getIdAd() == adId;
    }

    public Ad saveImages(Ad ad, List<MultipartFile> images, List<Integer> imagePositions, List<AdImage> adImages) {
        if (images != null && !images.isEmpty()) {
            if (imagePositions.size() != images.size())
                throw new RuntimeException("imagePosition need to be the same size as images.");

            List<String> filePaths = fileUploadService.saveFiles(images, FileUploadService.FileType.AD_IMAGE);
            if (filePaths == null) throw new RuntimeException("No files was saved.");
            if (filePaths.size() != images.size()) throw new RuntimeException("All images were not saved.");

            for (int i = 0; i < imagePositions.size(); i++) {
                adImages.add(adImageRepository.save(new AdImage(filePaths.get(i), imagePositions.get(i), true, ad)));
            }
        }

        return ad;
    }

    public void setFromAdCreator(AdCreator adCreator, Ad ad) {
        if (adCreator.adId() == null) {
            ad.setAddedDate(new Date(System.currentTimeMillis()));
            ad.setCustomer(customerRepository.findOneByIdCustomerAndIsDeletedFalseAndIsActivatedTrue(adCreator.customerId()));
        }

        ad.setTitle(adCreator.title());
        ad.setPrice(adCreator.price());
        ad.setAddress(adCreator.address());
        ad.setSold(adCreator.isSold());
        ad.setDescription(adCreator.description());
        ad.setAdTags(this.getAdTagsFromStringSet(adCreator.tags()));
        ad.setAdType(adTypeRepository.findOneByIdAdType(adCreator.adTypeId()));
        ad.setShape(adCreator.shape());
        ad.setVisibility(adCreator.visibility());
    }

    public void deleteAllImages(int idAd) {
        adImageRepository.deleteAllByAdIdAd(idAd);
    }

    public ResponseEntity<DisplayAdView> createOrUpdateAd(List<MultipartFile> images, List<Integer> imagePositions, @Valid AdCreator adCreator, boolean isUpdate) throws RuntimeException, JsonProcessingException {
        Ad ad = (isUpdate ? adRepo.findOneByIdAdAndIsDeletedFalse(adCreator.adId()) : new Ad());

        if (!this.isTitleConstraintOk(adCreator.title(), adCreator.customerId(), ad.getIdAd())) {
            throw new AdTitleUniqueException();
        }

        this.setFromAdCreator(adCreator, ad);

        // Create the list by getting the oldAdImages if it is an update, else new ArrayList.
        List<AdImage> adImages = (
            isUpdate ? new ObjectMapper().readValue(adCreator.adImagesJson(), new TypeReference<>() {
            }) : new ArrayList<>()
        );

        // To set the ad to each old image
        if (isUpdate) {
            this.deleteAllImages(ad.getIdAd());
            adImages.forEach(img -> {
                img.setAd(ad);
                img.setId(adImageRepository.save(img).getId());
            });
        }

        return new ResponseEntity<>(
            new DisplayAdView(this.saveImages(adRepo.save(ad), images, imagePositions, adImages)),
            HttpStatus.OK
        );
    }
}
