package com.opensell.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.opensell.entities.Ad;
import com.opensell.entities.ad.AdImage;
import com.opensell.entities.ad.AdTag;
import com.opensell.entities.dto.AdBuyerView;
import com.opensell.entities.dto.AdImgSave;
import com.opensell.entities.dto.AdModifView;
import com.opensell.entities.dto.AdSearchPreview;
import com.opensell.entities.verification.VerifyAdModif;
import com.opensell.entities.verification.VerifyCode;
import com.opensell.repository.AdRepository;
import com.opensell.repository.AdTagRepository;
import com.opensell.repository.adaptive.common.UpdateResult;
import com.opensell.service.AdService;
import com.opensell.service.FileUploadService;

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
	private AdService adService;

	/**
	 * Call function from AdService to get an AdBuyer from a link.
	 *
	 * @author Achraf
	 */
	@GetMapping("/get-ad-buyer-view/{link}")
	public AdBuyerView adBuyerView(@PathVariable String link) {
		return adService.getAdBuyerView(link);
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
			@RequestParam(required = false, defaultValue = "9999999d") Double priceMax,
			@RequestParam(required = false, defaultValue = "2020-01-01") Date dateMin,
			@RequestParam(required = false, defaultValue = "3000-01-01") Date dateMax,
			@RequestParam(required = false) Integer typeId, @RequestParam(required = false) Set<Integer> tagListId,
			@RequestParam(required = false) Integer shapeId,@RequestParam(required = false) Boolean filterSold,
			@RequestParam(required = false, defaultValue = "addedDate") String sortBy) {

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
							}
							;
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

	/**
	 * To change an Ad.
	 * 
	 * CODE : 0 = OK 30 = idCustomer Error 31 = Title Error 32 = Référence Error 33
	 * = Images Error 34 = Price Error 35 = Tags Error 36 = Type Error 37 =
	 * Description Error 38 = Visibilité Error 39 = Shape Error
	 * 
	 * @author Achraf
	 */
	@PatchMapping("/change")
	public List<Byte> changeAd(@RequestBody AdModifView adModifView, @RequestParam Integer idCustomer,
			@RequestParam(required = false) AdImgSave adImgSave) {
		try {
			List<Byte> errors = new ArrayList<>();
			if (adModifView != null) {
				Ad ad = adRepo.getAdByLink(adModifView.link());
				
				// Need to check reference
				if (ad != null && ad.getCustomer().getIdCustomer() == idCustomer) {
					// Check the title
					if (VerifyCode.SQL_ERROR == adService.changeTitle((title) -> ad.setTitle(title),
							adModifView.title(), idCustomer)) {
						errors.add(VerifyAdModif.TITLE_ERROR);
					}

					// Check the reference
					if (VerifyCode.SQL_ERROR == adService.changeReference((reference) -> ad.setReference(reference),
							adModifView.reference(), idCustomer)) {
						errors.add(VerifyAdModif.REFERENCE_ERROR);
					}
					
					// Images
					// if(VerifyCode.SQL_ERROR == adService.)
					
					// Check the price
					if(adModifView.price() >= 0) {
						ad.setPrice(adModifView.price());
					}
					
					// The new list of tags
					List<AdTag> adTags = new ArrayList<>();
					if(adModifView != null) {
						// Map over the set of string
						adModifView.tags().forEach(tag -> {
							// Get the old tag from the database
							AdTag tagTemp = adTagRepo.findByName(tag);
							
							// If the tag already exists
							if(tagTemp != null) adTags.add(tagTemp);

							// If the tag does exists
							// I am here, trying to deal when the tag is new
							else {
								if(tag.length() <= 255 && tag.length() > 0) {
									
								}
							}
						});
					}
				}
			}
			
			return errors;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	/**
	 * To change an Ad.
	 * 
	 * CODE : 0 = OK 30 = idCustomer Error 31 = Title Error 32 = Référence Error 33
	 * = Images Error 34 = Price Error 35 = Tags Error 36 = Type Error 37 =
	 * Description Error 38 = Visibilité Error 39 = Shape Error
	 * 
	 * @author Achraf
	 */
	@PatchMapping("/new-change")
	public UpdateResult changeAd(@RequestBody Map<String, Object> json, @RequestParam Integer idAd) {
		return adRepo.updateWithId(json, AdRepository.TABLE_INFO, idAd);
	}

	@PostMapping("/get-images")
	public boolean test(@RequestParam List<MultipartFile> files) throws Exception {
		FileUploadService.saveFile(files, root+FileUploadService.AD_IMAGE_PATH);
		/*
		 * Get a result like image/jpeg
		 * Can pe useful to determinate the extension of the file with 
		 * a enum that have 2 value one like image/jpeg and the other like .jpeg
		 * System.out.println(files.get(0).getContentType());*/
		return false;
	}
	
	@PostMapping("/test-map-json")
	public UpdateResult testMapJson(@RequestBody Map<String, Object> listOfMap, @RequestParam int idValue) {
		return adRepo.updateWithId(listOfMap, AdRepository.TABLE_INFO, idValue);
	}
}
