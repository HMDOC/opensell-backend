package com.opensell.service;

import com.opensell.ad.catalog.AdSearchPreview;
import com.opensell.model.Ad;
import com.opensell.model.ad.AdSearchParams;
import com.opensell.model.dto.AdBuyerView;
import com.opensell.repository.AdRepository;
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
@Validated
@RequiredArgsConstructor
public class AdService {
    private final AdRepository adRepo;

    /**
     * To get an AdBuyer from an id.
     *
     * @author Achraf
     */
    public ResponseEntity<?> getAdBuyerView(int idAd) {
        try {
            Ad ad = adRepo.findOneByIdAdAndIsDeletedFalse(idAd);

            if (ad == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ad not found");

            // need to do vérification for public when implementing Spring security.
			/*if(ad.getVisibility() == AdVisibility.PRIVATE.ordinal()) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not allowed to view this ad.");
			}*/

            return ResponseEntity.ok(new AdBuyerView(ad));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<AdSearchPreview> adSearch(AdSearchParams query) {

        List<Ad> adList = adRepo.getAdSearch(query.getQuery().toUpperCase(), query.getPriceMin(), query.getPriceMax(),
            query.getDateMin(), query.getDateMax(), query.getShapeId(), query.getTypeId(),
            query.getFilterSold(), Sort.by(query.getSortBy()));

        if (adList != null) {
            ArrayList searchTags = new ArrayList<String>((query.getAdTags() == null) ? null : query.getAdTags());

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
            System.out.println(query.getAdTags());
            //System.out.println(searchTags);
            if (query.isReverseSort()) {
                Collections.reverse(resultList);
            }

            return resultList;
        } else return null;
    }
}
