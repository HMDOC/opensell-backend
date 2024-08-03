package com.opensell.ad.catalog;

import com.opensell.ad.catalog.dto.AdPreviewDto;
import com.opensell.model.Ad;
import com.opensell.ad.catalog.dto.AdSearchParamsDto;
import com.opensell.ad.catalog.dto.AdViewDto;
import com.opensell.ad.AdRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Validated
public class CatalogService {
    private final AdRepository adRepo;

    public List<AdPreviewDto> adSearch(AdSearchParamsDto query) {
        System.out.println(query);
        List<Ad> adList = adRepo.getAdSearch(query.query().toUpperCase(), query.priceMin(), query.priceMax(),
            query.dateMin(), query.dateMax(), query.shapeId(), query.typeId(),
            query.filterSold(), Sort.by(query.sortBy()));

        if (adList != null) {
            ArrayList<String> searchTags = new ArrayList<>((query.tags() == null) ? null : query.tags());

            List<AdPreviewDto> resultList = new ArrayList<>(adList.size());

            for (Ad ad : adList) {
                if (searchTags != null && !searchTags.isEmpty()) {
                    if (!ad.getTags().containsAll(searchTags)) {
                        continue;
                    }
                }

                resultList.add(new AdPreviewDto(ad));
            }

            System.out.println(resultList.size());
            System.out.println(query.tags());
            //System.out.println(searchTags);
            if (query.reverseSort()==1) {
                Collections.reverse(resultList);
            }

            return resultList;
        } else return null;
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
