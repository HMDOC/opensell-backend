package com.opensell.controller;

import java.util.*;
import com.opensell.ad.catalog.AdSearchPreview;
import com.opensell.model.dto.*;
import com.opensell.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.opensell.model.Customer;
import com.opensell.model.ad.AdSearchParams;
import com.opensell.model.ad.AdTag;
import com.opensell.model.ad.AdType;
import com.opensell.service.AdService;

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
    @GetMapping("/get-ad-buyer-view/{idAd}")
    public ResponseEntity<?> adBuyerView(@PathVariable int idAd) {
        return adService.getAdBuyerView(idAd);
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
    @PostMapping("/search")
    public List<AdSearchPreview> adSearch(@RequestBody AdSearchParams query) {
        return adService.adSearch(query);
    }

    @GetMapping("/get-customer-ads/{customerId}")
    public List<DisplayAdView> getCustomerAds(@PathVariable Integer customerId) {
        Customer customer = customerRepo.findOneByIdCustomerAndIsDeletedFalseAndIsActivatedTrue(customerId);
        if (customer != null) {
            return adRepo.getCustomerAds(customer);
        } else return null;
    }
}
