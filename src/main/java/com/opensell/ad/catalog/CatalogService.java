package com.opensell.ad.catalog;

import com.opensell.ad.catalog.dto.AdPreviewDto;
import com.opensell.ad.listings.dto.AdPreviewProjectionDto;
import com.opensell.model.Ad;
import com.opensell.ad.catalog.dto.AdSearchParamsDto;
import com.opensell.ad.catalog.dto.AdViewDto;
import com.opensell.ad.AdRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Validated
public class CatalogService {
    private final AdRepository adRepo;

    public Page<AdPreviewProjectionDto> adSearch(AdSearchParamsDto query) {
        System.out.println(query);
        Pageable page = PageRequest.of(query.page()-1, 12, Sort.by(query.sortBy()));
        
        Page<AdPreviewProjectionDto> adList = adRepo.getAdSearch(
            query.query(), query.priceMin(), query.priceMax(),
            query.shape(), query.typeId(), query.filterSold(),
            query.tags(), page
        );
        
        return adList;
    }

    /**
     * To get an AdBuyer from an id.
     *
     * @author Achraf
     */
    public ResponseEntity<?> getAdBuyerView(String idAd) {
        try {
            Ad ad = adRepo.findOneByIdAndDeletedFalse(idAd);

            if (ad == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ad not found");

            // need to do vérification for public when implementing Spring security.
			/*if(ad.getVisibility() == AdVisibility.PRIVATE.ordinal()) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not allowed to view this ad.");
			}*/

            return ResponseEntity.ok(new AdViewDto(ad));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
