package com.opensell.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import com.opensell.entities.dto.adCreation.AdCreationData;
import com.opensell.entities.dto.adCreation.AdCreationFeedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.opensell.entities.Ad;
import com.opensell.entities.Customer;
import com.opensell.entities.ad.AdImage;
import com.opensell.entities.ad.AdTag;
import com.opensell.entities.ad.AdType;
import com.opensell.entities.dto.AdBuyerView;
import com.opensell.entities.dto.AdModifView;
import com.opensell.entities.dto.AdSearchPreview;
import com.opensell.entities.dto.DisplayAdView;
import com.opensell.entities.verification.HtmlCode;
import com.opensell.repository.AdRepository;
import com.opensell.repository.AdTagRepository;
import com.opensell.repository.AdTypeRepository;
import com.opensell.repository.CustomerRepository;
import com.opensell.service.AdModificationService;
import com.opensell.service.AdService;
import com.opensell.service.FileUploadService;
import com.opensell.service.AdModificationService.ModifType;
import com.opensell.service.FileUploadService.FileType;

@CrossOrigin(value = "http://localhost/")
@RestController
@RequestMapping("/ad")
public class AdController {
	@Value("${uploadPath}")
	public String root;
	
	@Autowired
	private AdRepository adRepo;

	@Autowired
	private AdTagRepository adTagRepo;

	@Autowired
	private AdTypeRepository adTypeRepo;

	@Autowired
	private AdService adService;

	@Autowired
	private AdModificationService adModif;

	@Autowired
	private CustomerRepository customerRepo;

	/**
	 * Call function from AdService to get an AdBuyer from a link.
	 *
	 * @author Achraf
	 */
	@GetMapping("/get-ad-buyer-view/{link}")
	public AdBuyerView adBuyerView(@PathVariable String link) {
		return adService.getAdBuyerView(link);
	}

	@GetMapping("/get-all-ad-type")
	public List<AdType> getAllTypes(){
		return adTypeRepo.findAll();
	}

	/**
	 * The http request that gets the entire list of ads, filtered by the provided
	 * parameters
	 * 
	 * @author Davide
	 */
	@GetMapping("/search")
	public List<AdSearchPreview> adSearch(@RequestParam(required = true) String query,
			@RequestParam(required = false, defaultValue = "0") Double priceMin,
			@RequestParam(required = false, defaultValue = "99990d") Double priceMax,
			@RequestParam(required = false, defaultValue = "2020-01-01") Date dateMin,
			@RequestParam(required = false, defaultValue = "3000-01-01") Date dateMax,
			@RequestParam(required = false) Integer typeId, @RequestParam(required = false) Set<Integer> tagListId,
			@RequestParam(required = false) Integer shapeId,@RequestParam(required = false) Boolean filterSold,
			@RequestParam(required = false, defaultValue = "addedDate") String sortBy, 
			@RequestParam(required = false, defaultValue = "false") boolean reverseSort) {

		List<Ad> adList = adRepo.getAdSearch(query.toUpperCase(), priceMin, priceMax, dateMin, dateMax, shapeId,
				typeId, filterSold, Sort.by(sortBy));

		if (adList != null) {
			List<AdSearchPreview> resultList = new ArrayList<>(adList.size());

			for (Ad ad : adList) {
				// Shortcuts for variables
				double price = ad.getPrice();
				int shape = ad.getShape();
				Date date = ad.getAddedDate();
				int type = ad.getAdType().getIdAdType();

				boolean hasTag = true;

				if (tagListId != null) {
					hasTag = false;
					for (Integer tagId : tagListId) {
						for (AdTag adTag : ad.getAdTags()) {
							if (adTag.getIdAdTag() == tagId) {
								hasTag = true;
								break;
							};
						}
						if (hasTag) {
							break;
						}
					}
				}

				// Filter results
				if (!hasTag) {
					System.out.println("Failed");
					continue;
				} else {
					System.out.println("Passed");
				}
				
				String imagePath = "";
				
				for(AdImage image : ad.getAdImages()) {
					if (image.getSpot()==0) {
						imagePath = image.getPath();
						break;
					}
				}
				
				resultList.add(new AdSearchPreview(ad.getTitle(), price, shape, ad.isSold(), ad.getLink(), imagePath));
			}
			System.out.println(resultList.size());
			if (reverseSort) {
				Collections.reverse(resultList);
			}
			return resultList;
		} else {
			return null;
		}
	}

	/**
	 * To get an adView when a user want to modify an ad.
	 * 
	 * @author Achraf
	 */
	@GetMapping("/to-modify/{link}")
	public AdModifView getAdModifView(@PathVariable String link) {
		Ad ad = adRepo.getAdToModif(link);

		if (ad != null) {
			Set<String> adTagsName = new LinkedHashSet<>();
			ad.getAdTags().forEach(tag -> adTagsName.add(tag.getName()));

			List<String> adImagesPath = new ArrayList<>();
			ad.getAdImages().forEach(image -> adImagesPath.add(image.getPath()));

			return new AdModifView(ad.getIdAd(), ad.getTitle(), ad.getPrice(), ad.getShape(), ad.isSold(), ad.getVisibility(),
					ad.getDescription(), ad.getReference(), ad.getAddress(), ad.getLink(), ad.getAdType().getName(),
					adTagsName, adImagesPath);
		}

		return null;
	}

	@PostMapping("/get-images")
	public boolean test(@RequestParam List<MultipartFile> files) throws Exception {
		FileUploadService.saveFile(files, FileType.AD_IMAGE, root);
		/*
		 * Get a result like image/jpeg
		 * Can pe useful to determinate the extension of the file with 
		 * a enum that have 2 value one like image/jpeg and the other like .jpeg
		 * System.out.println(files.get(0).getContentType());*/
		return false;
	}

	/**
	 * Function to modify element of an ad.
	 * 
	 * @param modifType What is the field you want to modify.
	 * @param value The new value of the field.
	 * @param idAd The id of the row.
	 * @return ResultCode
	 * @author Achraf
	 */
	@PatchMapping("/modification")
	public int adModification(@RequestParam int modifType, @RequestParam Object value, @RequestParam int idAd) {
		switch (modifType) {
			case ModifType.TITLE -> { return adModif.changeTitle(value.toString(), idAd); }
			case ModifType.REFERENCE -> { return adModif.changeReference(value.toString(), idAd); }
			case ModifType.PRICE -> { return adModif.changePrice(Double.parseDouble(value.toString()), idAd); }
			case ModifType.AD_TYPE -> { 
				// NOT FINISHED
				return adModif.changeAdType(value.toString(), idAd);
			}
			case ModifType.ADDRESS -> { return adModif.changeAddress(value.toString(), idAd); }
			case ModifType.IS_SOLD -> { System.out.println(Boolean.valueOf(value.toString())); return adModif.changeIsSold(Boolean.parseBoolean(value.toString()), idAd); }
			case ModifType.DESCRIPTION -> { return adModif.changeDescription(value.toString(), idAd); }
			case ModifType.AD_IMAGES -> { 
				try {
					// NOT FINISHED
					return adModif.changeAdImages(adModif.readListFromJson(String.class, value), idAd); 
				} catch (Exception e) {
					e.printStackTrace();
					return HtmlCode.SERVER_ERROR;
				}

			}
			case ModifType.AD_TAGS -> { 
				try {
				// NOT FINISHED
					return adModif.changeAdTags(adModif.readListFromJson(String.class, value), idAd); 
			
				} catch(Exception e) {
					e.printStackTrace();
					return HtmlCode.SERVER_ERROR;
				}
			}
			case ModifType.VISIBILITY -> { return adModif.changeVisibility(Integer.parseInt(value.toString()), idAd); }
			case ModifType.SHAPE -> { return adModif.changeShape(Integer.parseInt(value.toString()), idAd); }
			default -> { return HtmlCode.BAD_REQUEST; }
		}
	}
	
	@GetMapping("/get-customer-ads/{customerId}")
	public List<DisplayAdView> getCustomerAds(@PathVariable Integer customerId) {
		Customer customer = customerRepo.findOneByIdCustomerAndIsDeletedFalse(customerId);
		if(customer != null) {
			return adRepo.getCustomerAds(customer);
		} else return null;
	}

	@PatchMapping("/delete-ad/{idAd}")
	public boolean deleteAd(@PathVariable int idAd) {
		return adRepo.hideAd(idAd) > 0;
	}

	@PostMapping("/create-ad")
	public AdCreationFeedback createAd(@RequestBody(required = true) AdCreationData data) {
		return adService.saveAd(data);
	}
}
