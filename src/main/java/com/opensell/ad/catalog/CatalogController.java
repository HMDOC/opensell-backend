package com.opensell.ad.catalog;

import com.opensell.ad.catalog.dto.AdPreviewDto;
import com.opensell.ad.catalog.dto.AdSearchParamsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/ad/catalog")
@RequiredArgsConstructor
public class CatalogController {
    private final CatalogService catalogService;

    /**
     * Call function from AdService to get an AdBuyer from a link.
     *
     * @author Achraf
     */
    @GetMapping("/{idAd}")
    public ResponseEntity<?> getAdBuyerView(@PathVariable String idAd) {
        return catalogService.getAdBuyerView(idAd);
    }

    /**
     * The http request that gets the entire list of ads, filtered by the provided
     * parameters
     *
     * @author Davide
     */
    @PostMapping("/search")
    public List<?> adSearch(@RequestBody AdSearchParamsDto query) {
        return catalogService.adSearch(query);
    }
}
