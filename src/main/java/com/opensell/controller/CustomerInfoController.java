package com.opensell.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.opensell.entities.dto.AdSearchPreview;
import java.util.List;
import java.util.ArrayList;

import com.opensell.entities.Ad;
import com.opensell.entities.customer.CustomerInfo;
import com.opensell.repository.AdRepository;
import com.opensell.repository.CustomerInfoRepository;

@RestController
@CrossOrigin(value = "http://localhost/")
@RequestMapping("/user")
public class CustomerInfoController {
    
    @Autowired
    private CustomerInfoRepository customerInfoRepository;

    @Autowired
    private AdRepository adRep;

    @GetMapping("/{link}")
    public CustomerInfo getCustomerInfo(@PathVariable String link) {
        return customerInfoRepository.findCustomerInfoByLink(link);
    }
    @GetMapping("/{link}/public-user-ads")
    public List<AdSearchPreview> getUserAds(@PathVariable String link) {
        List<Ad> adList = adRep.getAdsFromUser(link);
        if (adList != null) {
            List<AdSearchPreview> safeAdsList = new ArrayList<>(adList.size());
            for (Ad ad : adList) {
                safeAdsList.add(new AdSearchPreview(ad));
            }
            return safeAdsList;
        } else {
            return null;
        }
        
    }
}
