package com.opensell.service;

import java.sql.Date;
import java.util.*;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.opensell.entities.ad.AdImage;
import com.opensell.entities.ad.AdShape;
import com.opensell.entities.dto.AdCreator;
import com.opensell.entities.dto.DisplayAdView;
import com.opensell.entities.dto.adCreation.AdCreationData;
import com.opensell.entities.dto.adCreation.AdCreationFeedback;
import com.opensell.entities.verification.HtmlCode;
import com.opensell.exception.AdTitleUniqueException;
import com.opensell.repository.CustomerRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.opensell.entities.Ad;
import com.opensell.entities.ad.AdTag;
import com.opensell.entities.dto.AdBuyerView;
import com.opensell.repository.AdRepository;
import com.opensell.repository.AdTagRepository;
import com.opensell.repository.AdTypeRepository;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.function.EntityResponse;

/**
 * This service is used to
 */
@Service
@Validated
public class AdService {
	@Autowired
	private AdRepository adRepo;

	@Autowired
	private AdTagRepository adTagRepo;

	@Autowired
	private AdTypeRepository adTypeRepo;

	@Autowired
	private LinkGenerator linkGenerator;

	@Autowired
	private AdModificationService adModificationService;
    @Autowired
    private CustomerRepository customerRepository;

	@Autowired
	private FileUploadService fileUploadService;

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

	/**
	 *
	 * @author Olivier Mansuy
	 */
	public AdCreationFeedback saveAd(AdCreationData data) {
		int result = 0;
		int currentAdId;
		try {
			if (adRepo.checkTitle(data.customerId(), data.title()) == 0) {
				result = result + adRepo.createAd(data.adTypeId(),
						data.customerId(), data.price(),
						data.shape(), data.visibility(),
						data.title(), data.description(),
						data.address(),
						linkGenerator.generateAdLink(),
						linkGenerator.generateAdLink());
				//could be replaced by .save ...
				currentAdId = adRepo.getAdIdFromTitleAndCustomerID(data.customerId(), data.title());
				result+=insertTags(data.tags(), currentAdId);
			} else throw new Exception("title already exists...");
			return new AdCreationFeedback(HtmlCode.SUCCESS, result, null, currentAdId);
		} catch (Exception e) {
			return new AdCreationFeedback(HtmlCode.FAILURE, result, e.getMessage(), null);
		}
	}

	private int insertTags(String[] tagsAsNames, int currentAdId) {
		int res = 0;
		if (tagsAsNames.length > 0) {
			LinkedHashSet<AdTag> tagsAsObjects = new LinkedHashSet<>();
			AdTag tempTag;
			for (String name : tagsAsNames) {
				tempTag = adTagRepo.findOneByName(name);
				if (tempTag != null) tagsAsObjects.add(tempTag);
				else tagsAsObjects.add(adTagRepo.save(new AdTag(name)));
			}
			for (AdTag tag : tagsAsObjects) res = res + adRepo.saveRelAdTag(currentAdId, tag.getIdAdTag());
		}
		return res;
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
		ad.setAdTags(adModificationService.getAdTagsFromStringList(adCreator.tags()));
		ad.setAdType(adTypeRepo.findOneByIdAdType(adCreator.adTypeId()));
		ad.setShape(adCreator.shape());
		ad.setVisibility(adCreator.visibility());
	}

	public ResponseEntity<DisplayAdView> createOrUpdateAd(List<MultipartFile> images, List<Integer> imagePositions, @Valid AdCreator adCreator
	) throws RuntimeException {
		Ad ad = (adCreator.adId() == null ? new Ad() : adRepo.findOneByIdAdAndIsDeletedFalse(adCreator.adId()));

		if(adRepo.checkTitle(adCreator.customerId(), adCreator.title()) == 1) {
			throw new AdTitleUniqueException();
		}

		setFromAdCreator(adCreator, ad);

		List<AdImage> adImages = new ArrayList<>();
		if(images != null && !images.isEmpty()) {
			List<String> filePaths = fileUploadService.saveFiles(images, FileUploadService.FileType.AD_IMAGE);
			if (filePaths == null) throw new RuntimeException("No files was saved.");
			if(filePaths.size() != imagePositions.size()) throw new RuntimeException("All images were not saved.");

			for (int i = 0; i < imagePositions.size(); i++) {
				adImages.add(new AdImage(filePaths.get(i), imagePositions.get(i), true, ad));
			}
		}

		// Il reste à gérer les anciennes images.
		// Tout ce qu'il faut faire, c'est de convertir les String images en AdImages et directement les rajouter dans la liste
		

		adRepo.save(ad);
		return new ResponseEntity<>(new DisplayAdView(ad), HttpStatus.OK);
	}
}
