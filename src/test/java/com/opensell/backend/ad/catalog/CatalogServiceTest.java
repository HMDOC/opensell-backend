package com.opensell.backend.ad.catalog;

import com.opensell.ad.AdRepository;
import com.opensell.ad.catalog.CatalogService;

import static com.opensell.backend.BackendApplicationTests.getAdExample;
import static org.junit.jupiter.api.Assertions.*;

import com.opensell.ad.catalog.dto.AdViewDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

@ExtendWith(MockitoExtension.class)
public class CatalogServiceTest {
    @Mock
    private AdRepository adRepository;

    @InjectMocks
    private CatalogService catalogService;

    @Test
    public void givenInvalidId_whenGetAdBuyerView_thenResponseEntityWithNotFound() {
        when(adRepository.findOneByIdAndDeletedFalse(anyString())).thenReturn(null);
        assertEquals(catalogService.getAdBuyerView("INVALID_ID").getStatusCode(), HttpStatusCode.valueOf(404));;
    }

    @Test
    public void givenValidId_whenGetAdBuyerView_thenResponseEntityWithNotFound() throws IOException {
        var fakeAd = getAdExample();
        when(adRepository.findOneByIdAndDeletedFalse(fakeAd.getId())).thenReturn(fakeAd);

        var response = catalogService.getAdBuyerView(fakeAd.getId());
        var statusCodeValue = response.getStatusCode().value();
        var body = (AdViewDto) response.getBody();

        assertTrue(statusCodeValue == 200 && body.adTitle().equals(fakeAd.getTitle()));
    }
}
