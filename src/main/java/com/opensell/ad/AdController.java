package com.opensell.ad;

import com.opensell.model.AdCategory;
import com.opensell.repository.AdCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@Validated
@RequestMapping("/api/ad")
@RequiredArgsConstructor
public class AdController {
    private final AdCategoryRepository adTypeRepo;

    @GetMapping("/get-all-ad-type")
    public List<AdCategory> getAllTypes() {
        return adTypeRepo.findAll();
    }
}
