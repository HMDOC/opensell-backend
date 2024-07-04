package com.opensell.controller;

import java.sql.Date;
import java.util.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.opensell.model.dto.*;
import com.opensell.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.opensell.model.Ad;
import com.opensell.model.Customer;
import com.opensell.model.ad.AdTag;
import com.opensell.model.ad.AdType;
import com.opensell.service.AdService;

@CrossOrigin("${allowedUrl}")
@RestController
@Validated
@RequestMapping("/ad")
@RequiredArgsConstructor
public class AdController {
    private final AdRepository adRepo;
    private final AdTagRepository adTagRepo;
    private final AdTypeRepository adTypeRepo;
    private final AdService adService;
    private final CustomerRepository customerRepo;

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

    @PostMapping("/v2/create-or-update-ad")
    public ResponseEntity<DisplayAdView> createOrUpdateAd(@RequestBody(required = false) List<MultipartFile> images,
                                                          @RequestParam(required = false) List<Integer> imagePositions,
                                                          AdCreator adCreator) throws JsonProcessingException {
        return adService.createOrUpdateAd(images, imagePositions, adCreator);
    }
}
