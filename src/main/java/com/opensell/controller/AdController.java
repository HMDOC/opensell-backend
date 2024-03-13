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
import com.opensell.entities.ad.AdType;
import com.opensell.entities.dto.AdBuyerView;
import com.opensell.entities.dto.AdModifView;
import com.opensell.entities.dto.AdSearchPreview;
import com.opensell.repository.AdRepository;
import com.opensell.repository.AdTagRepository;
import com.opensell.repository.AdTypeRepository;
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
	private AdTypeRepository adTypeRepo;

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
	public UpdateResult testMapJson(@RequestBody Map<String, Object> json, @RequestParam int idValue) {
		UpdateResult jdbcUpdateResult = adRepo.updateWithId(json, AdRepository.TABLE_INFO, idValue);
		
		Map<String, Object> hibernateQuery = jdbcUpdateResult.getHibernateJson();
		if(hibernateQuery != null) {
			Ad ad = adRepo.findOneByIdAdAndIsDeletedFalse(1);
			
			boolean isAdChanged = false;
			if(ad != null) {
				isAdChanged = adService.changeAdType((String) hibernateQuery.get("adType"), ad);
				System.out.println(isAdChanged);
				if(isAdChanged) adRepo.save(ad);
			}
		}

		return jdbcUpdateResult;
		/*
			OLD code from old way but can be useful to deal with the hibernate part

			List<AdTag> adTags = new ArrayList<>();
				if(adModifView != null) {
					// Map over the set of string
					adModifView.adTags().forEach(tag -> {
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
		*/
	}

	@GetMapping("/test")
	public Ad test() {
		return adRepo.findOneByIdAdAndIsDeletedFalse(1);
	}
}
