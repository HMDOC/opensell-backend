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

@Service
@RequiredArgsConstructor
@Validated
public class CatalogService {
    private final AdRepository adRepo;

    public Page<AdPreviewProjectionDto> adSearch(AdSearchParamsDto query) {
        Sort sort = Sort.by(query.sortBy());
        sort = (query.reverseSort()>0) ? sort.descending() : sort.ascending();
        Pageable page = PageRequest.of(query.page()-1, 12, sort);
        
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
        Ad ad = adRepo.findOneByIdAndDeletedFalse(idAd);

        if (ad == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ad not found");

        // need to do vérification for public when implementing Spring security.
        /*if(ad.getVisibility() == AdVisibility.PRIVATE.ordinal()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not allowed to view this ad.");
        }*/

        return ResponseEntity.ok(new AdViewDto(ad));
    }
}
