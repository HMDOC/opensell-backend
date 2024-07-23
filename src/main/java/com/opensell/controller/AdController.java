package com.opensell.controller;

import java.util.*;
import com.opensell.model.dto.*;
import com.opensell.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.opensell.model.Customer;
import com.opensell.model.ad.AdTag;
import com.opensell.model.ad.AdType;

@RestController
@Validated
@RequestMapping("/api/ad")
@RequiredArgsConstructor
public class AdController {
    private final AdRepository adRepo;
    private final AdTagRepository adTagRepo;
    private final AdTypeRepository adTypeRepo;
    private final CustomerRepository customerRepo;

    @GetMapping("/get-all-ad-type")
    public List<AdType> getAllTypes() {
        return adTypeRepo.findAll();
    }

    @GetMapping("/get-all-ad-tag")
    public List<AdTag> getAllTags() {
        return adTagRepo.findAll();
    }

    @GetMapping("/get-customer-ads/{customerId}")
    public List<DisplayAdView> getCustomerAds(@PathVariable Integer customerId) {
        Customer customer = customerRepo.findOneByIdCustomerAndIsDeletedFalseAndIsActivatedTrue(customerId);
        if (customer != null) {
            return adRepo.getCustomerAds(customer);
        } else return null;
    }
}
