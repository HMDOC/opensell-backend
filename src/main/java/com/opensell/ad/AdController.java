package com.opensell.ad;

import com.opensell.model.ad.AdType;
import com.opensell.repository.AdTypeRepository;
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
    private final AdTypeRepository adTypeRepo;

    @GetMapping("/get-all-ad-type")
    public List<AdType> getAllTypes() {
        return adTypeRepo.findAll();
    }
}
