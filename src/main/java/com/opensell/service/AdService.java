package com.opensell.service;

import java.sql.Date;
import java.util.*;
import java.util.List;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opensell.model.ad.AdImage;
import com.opensell.model.dto.AdCreator;
import com.opensell.model.dto.DisplayAdView;
import com.opensell.exception.AdTitleUniqueException;
import com.opensell.repository.AdImageRepository;
import com.opensell.repository.CustomerRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.opensell.model.Ad;
import com.opensell.model.dto.AdBuyerView;
import com.opensell.repository.AdRepository;
import com.opensell.repository.AdTypeRepository;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

@Service
@Validated
@RequiredArgsConstructor
public class AdService {
	private final AdRepository adRepo;
	private final AdTypeRepository adTypeRepo;
	private final LinkGenerator linkGenerator;
	private final AdModificationService adModificationService;
    private final CustomerRepository customerRepository;
	private final FileUploadService fileUploadService;
	private final AdImageRepository adImageRepository;

	public boolean isTitleConstraintOk(String title, int userId) {
		return adRepo.findOneByTitleAndCustomerIdCustomerAndIsDeletedFalse(title, userId).isEmpty();
	}

	public void deleteAllImages(int idAd) {
		adImageRepository.deleteAllByAdIdAd(idAd);
	}

	/**
	 * To get an AdBuyer from a link.
	 *
	 * @author Achraf
	 */
	public AdBuyerView getAdBuyerView(String link) {
		try {
			Ad ad = adRepo.getAdByLink(link);
			if(ad != null) {
				return new AdBuyerView(ad);
			} 
			
			else return null;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public void setFromAdCreator(AdCreator adCreator, Ad ad) {
		if(adCreator.adId() == null) {
			ad.setLink(linkGenerator.generateAdLink());
			ad.setAddedDate(new Date(System.currentTimeMillis()));
			ad.setCustomer(customerRepository.findOneByIdCustomerAndIsDeletedFalseAndIsActivatedTrue(adCreator.customerId()));
		}

		ad.setTitle(adCreator.title());
		ad.setPrice(adCreator.price());
		ad.setAddress(adCreator.address());
		ad.setSold(adCreator.isSold());
		ad.setDescription(adCreator.description());
		ad.setAdTags(adModificationService.getAdTagsFromStringSet(adCreator.tags()));
		ad.setAdType(adTypeRepo.findOneByIdAdType(adCreator.adTypeId()));
		ad.setShape(adCreator.shape());
		ad.setVisibility(adCreator.visibility());
	}

	public Ad saveImages(Ad ad, List<MultipartFile> images, List<Integer> imagePositions, List<AdImage> adImages) {
		if(images != null && !images.isEmpty()) {
			if(imagePositions.size() != images.size()) throw new RuntimeException("imagePosition need to be the same size as images.");

			List<String> filePaths = fileUploadService.saveFiles(images, FileUploadService.FileType.AD_IMAGE);
			if (filePaths == null) throw new RuntimeException("No files was saved.");
			if(filePaths.size() != images.size()) throw new RuntimeException("All images were not saved.");

			for (int i = 0; i < imagePositions.size(); i++) {
				adImages.add(adImageRepository.save(new AdImage(filePaths.get(i), imagePositions.get(i), true, ad)));
			}
		}

		return ad;
	}

	public ResponseEntity<DisplayAdView> createOrUpdateAd(List<MultipartFile> images, List<Integer> imagePositions, @Valid AdCreator adCreator
	) throws RuntimeException, JsonProcessingException {
		boolean isUpdate = adCreator.adId() != null;
		Ad ad = (isUpdate ? adRepo.findOneByIdAdAndIsDeletedFalse(adCreator.adId()) : new Ad());

		if(
			// if create or title as changed.
			(!isUpdate || !ad.getTitle().equals(adCreator.title())) &&
			// check constraint
			!isTitleConstraintOk(adCreator.title(), adCreator.customerId())
		) {
			throw new AdTitleUniqueException();
		}

		setFromAdCreator(adCreator, ad);

        // Create the list by getting the oldAdImages if it is an update, else new ArrayList.
        List<AdImage> adImages = (
			isUpdate ? new ObjectMapper().readValue(adCreator.adImagesJson(), new TypeReference<>() {}) : new ArrayList<>()
		);

		// To set the ad to each old image
		if(isUpdate) {
			deleteAllImages(ad.getIdAd());
			adImages.forEach(img -> {img.setAd(ad); img.setId(adImageRepository.save(img).getId());});
		}

		return new ResponseEntity<>(
			new DisplayAdView(saveImages(adRepo.save(ad), images, imagePositions, adImages)),
			HttpStatus.OK
		);
	}
}
