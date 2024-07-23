package com.opensell.controller;

import java.util.*;
import com.opensell.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.opensell.model.ad.AdTag;
import com.opensell.model.ad.AdType;

@RestController
@Validated
@RequestMapping("/api/ad")
@RequiredArgsConstructor
public class AdController {
    private final AdTagRepository adTagRepo;
    private final AdTypeRepository adTypeRepo;

    @GetMapping("/get-all-ad-type")
    public List<AdType> getAllTypes() {
        return adTypeRepo.findAll();
    }

    @GetMapping("/get-all-ad-tag")
    public List<AdTag> getAllTags() {
        return adTagRepo.findAll();
    }
}
