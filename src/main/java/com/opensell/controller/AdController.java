package com.opensell.controller;

import java.sql.Date;
import java.util.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.opensell.entities.dto.*;
import com.opensell.entities.dto.adCreation.AdCreationData;
import com.opensell.entities.dto.adCreation.AdCreationFeedback;
import com.opensell.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.opensell.entities.Ad;
import com.opensell.entities.Customer;
import com.opensell.entities.ad.AdImage;
import com.opensell.entities.ad.AdTag;
import com.opensell.entities.ad.AdType;
import com.opensell.entities.verification.HtmlCode;
import com.opensell.service.AdModificationService;
import com.opensell.service.FileUploadService;
import com.opensell.service.AdModificationService.ModifType;
import com.opensell.service.AdService;
import com.opensell.service.FileUploadService.FileType;

@CrossOrigin("${allowedUrl}")
@RestController
@Validated
@RequestMapping("/ad")
public class AdController {
    @Autowired
    private AdRepository adRepo;

    @Autowired
    private AdImageRepository adImageRepo;

    @Autowired
    private AdTagRepository adTagRepo;

    @Autowired
    private AdTypeRepository adTypeRepo;

    @Autowired
    private AdService adService;

    @Autowired
    private AdModificationService adModif;

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private FileUploadService fileUploadService;

    /**
     * Call function from AdService to get an AdBuyer from a link.
     *
     * @author Achraf
     */
    @GetMapping("/get-ad-buyer-view/{link}")
    public AdBuyerView adBuyerView(@PathVariable String link) {
        return adService.getAdBuyerView(link);
    }

    @GetMapping("/get-all-ad-type")
    public List<AdType> getAllTypes() {
        return adTypeRepo.findAll();
    }

    @GetMapping("/get-all-ad-tag")
    public List<AdTag> getAllTags() {
        return adTagRepo.findAll();
    }

    /**
     * The http request that gets the entire list of ads, filtered by the provided
     * parameters
     *
     * @author Davide
     */
    @GetMapping("/search")
    public List<AdSearchPreview> adSearch(@RequestParam(required = true) String query,
                                          @RequestParam(required = false, defaultValue = "0") Double priceMin,
                                          @RequestParam(required = false, defaultValue = "99990d") Double priceMax,
                                          @RequestParam(required = false, defaultValue = "2020-01-01") Date dateMin,
                                          @RequestParam(required = false, defaultValue = "3000-01-01") Date dateMax,
                                          @RequestParam(required = false) Integer typeId,
										  @RequestParam(required = false, defaultValue="") List<String> adTags,
                                          @RequestParam(required = false) Integer shapeId, 
                                          @RequestParam(required = false) Boolean filterSold,
                                          @RequestParam(required = false, defaultValue = "addedDate") String sortBy,
                                          @RequestParam(required = false, defaultValue = "false") boolean reverseSort) {

        List<Ad> adList = adRepo.getAdSearch(query.toUpperCase(), priceMin, priceMax, dateMin, dateMax, shapeId,
                typeId, filterSold, Sort.by(sortBy));

        if (adList != null) {
			ArrayList searchTags = new ArrayList<String>( (adTags==null) ? null : adTags);
			
            List<AdSearchPreview> resultList = new ArrayList<>(adList.size());

            for (Ad ad : adList) {
                if (searchTags != null && !searchTags.isEmpty()) {
                    if (!ad.getTagsName().containsAll(searchTags)) {
                        continue;
                    }
                }

                resultList.add(new AdSearchPreview(ad));
            }

            System.out.println(resultList.size());
            System.out.println(adTags);
            //System.out.println(searchTags);
            if (reverseSort) {
                Collections.reverse(resultList);
            }

            return resultList;
        } else return null;
    }

    /**
     * To get an adView when a user want to modify an ad.
     *
     * @author Achraf
     */
    @GetMapping("/to-modify/{link}")
    public AdCreator getAdModifyView(@PathVariable String link) throws JsonProcessingException {
        Ad ad = adRepo.getAdToModif(link);
        return ad != null ? AdCreator.fromAd(ad) : null ;
    }

    @Deprecated(forRemoval = true)
    @PatchMapping("/modification/tags")
    public int adChangeTags(@RequestBody List<String> tags, @RequestParam int idAd) {
        return adModif.changeAdTags(tags, idAd);
    }

    /**
     * Function to modify element of an ad.
     *
     * @param modifType What is the field you want to modify.
     * @param idAd      The id of the row.
     * @return ResultCode
     * @author Achraf
     */
    @Deprecated(forRemoval = true)
    @PatchMapping("/modification")
    public int adModification(@RequestBody AdModificationService.ModifBody modifBody, @RequestParam int idAd, @RequestParam int modifType) {
        switch (modifType) {
            case ModifType.TITLE -> {
                return adModif.changeTitle(modifBody.value.toString(), idAd);
            }
            case ModifType.PRICE -> {
                return adModif.changePrice(Double.parseDouble(modifBody.value.toString()), idAd);
            }
            case ModifType.AD_TYPE -> {
                return adModif.changeAdType((Map<String, Object>) modifBody.value, idAd);
            }
            case ModifType.ADDRESS -> {
                return adModif.changeAddress(modifBody.value.toString(), idAd);
            }
            case ModifType.IS_SOLD -> {
                System.out.println(Boolean.valueOf(modifBody.value.toString()));
                return adModif.changeIsSold(Boolean.parseBoolean(modifBody.value.toString()), idAd);
            }
            case ModifType.DESCRIPTION -> {
                return adModif.changeDescription(modifBody.value.toString(), idAd);
            }
            case ModifType.VISIBILITY -> {
                return adModif.changeVisibility(Integer.parseInt(modifBody.value.toString()), idAd);
            }
            case ModifType.SHAPE -> {
                return adModif.changeShape(Integer.parseInt(modifBody.value.toString()), idAd);
            }
            default -> {
                return HtmlCode.BAD_REQUEST;
            }
        }
    }

    // ONLY FOR THE AD OWNER NOT FOR THE NORMAL USER
    @GetMapping("/get-ad-preview-for-customer/{idAd}")
    public AdBuyerView getUserPreviewAd(@PathVariable int idAd) {
        return new AdBuyerView(adRepo.findOneByIdAdAndIsDeletedFalse(idAd));
    }

    @GetMapping("/get-customer-ads/{customerId}")
    public List<DisplayAdView> getCustomerAds(@PathVariable Integer customerId) {
        Customer customer = customerRepo.findOneByIdCustomerAndIsDeletedFalseAndIsActivatedTrue(customerId);
        if (customer != null) {
            return adRepo.getCustomerAds(customer);
        } else return null;
    }

    @PatchMapping("/delete-ad/{idAd}")
    public boolean deleteAd(@PathVariable int idAd) {
        return adRepo.hideAd(idAd) > 0;
    }

    @Deprecated(forRemoval = true)
    @PostMapping("/create-ad")
    public AdCreationFeedback createAd(@RequestBody AdCreationData data) {
        return adService.saveAd(data);
    }

    @Deprecated(forRemoval = true)
    @PostMapping("/save-ad-images")
    public List<AdImage> saveAdImages(
                                @RequestBody(required = false) List<MultipartFile> adImages,
                                @RequestParam int idAd,
                                @RequestParam boolean isModif,
                                @RequestParam(required = false) List<Integer> idsToDelete) {
        try {
            boolean isAdChanged = false;

            // Get the ad.
            Ad ad = adRepo.findOneByIdAdAndIsDeletedFalse(idAd);
            if (ad == null) throw new Exception("idAd not found");
            List<AdImage> adPictures = ad.getAdImages();

            if(adPictures == null || !isModif) adPictures = new ArrayList<>();
            else if(idsToDelete != null && !idsToDelete.isEmpty()) {
                adPictures.removeIf(img -> {
                    if(idsToDelete.contains(img.getIdAdImage())) {
                        adImageRepo.delete(img);
                        return true;
                    }

                    else return false;
                });

                System.out.println(adPictures);
                isAdChanged = true;
            }

            // Save Files
            if (adImages != null && !adImages.isEmpty()) {
                List<String> filePaths = fileUploadService.saveFiles(adImages, FileType.AD_IMAGE);
                if (filePaths == null) throw new Exception("No files was saved.");

                int spot = 0;
                if (isModif) {
                    // NEED TO REDO SPOT FOR MODIF BECAUSE WHEN DELETE THIS IS INVALID
                    // Spot is equal to the last spot + 1
                    if(!adPictures.isEmpty()) spot = adPictures.getLast().getSpot() + 1;
                }

                for (String path : filePaths) {
                    adPictures.add(adImageRepo.save(new AdImage(path, spot, true, ad)));
                    spot++;
                }

                isAdChanged = true;
            }

            if(isAdChanged && isModif) {
                int currentSpot = 0;
                for(AdImage adImage : adPictures) {
                    adImage.setSpot(currentSpot);
                    currentSpot++;
                }
                
                ad.setAdImages(adPictures);
                return adRepo.save(ad).getAdImages();
            }
            else return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/v2/update")
    public ResponseEntity<DisplayAdView> updateV2(@RequestBody(required = false) List<MultipartFile> images,
                                                  @RequestParam(required = false) List<Integer> imagePositions,
                                                  AdCreator adCreator) throws JsonProcessingException {
        return adService.createOrUpdateAd(images, imagePositions, adCreator);
    }
}
