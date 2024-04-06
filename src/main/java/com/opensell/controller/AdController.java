package com.opensell.controller;
import java.sql.Array;
import java.sql.Date;
import java.util.*;

import com.opensell.entities.dto.adCreation.AdCreationData;
import com.opensell.entities.dto.adCreation.AdCreationFeedback;
import com.opensell.repository.*;
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
	private AdImageRepository adImageRepo;

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

	@GetMapping("/get-all-ad-tag")
	public List<AdTag> getAllTags(){
		return adTagRepo.findAll();
	}

	/**
	 * The http request that gets the entire list of ads, filtered by the provided
	 * parameters
	 * 
	 * @author Davide
	 */
	@PostMapping("/search")
	public List<AdSearchPreview> adSearch(@RequestBody(required = false) List<String> searchTags, @RequestParam(required = true) String query,
			@RequestParam(required = false, defaultValue = "0") Double priceMin,
			@RequestParam(required = false, defaultValue = "99990d") Double priceMax,
			@RequestParam(required = false, defaultValue = "2020-01-01") Date dateMin,
			@RequestParam(required = false, defaultValue = "3000-01-01") Date dateMax,
			@RequestParam(required = false) Integer typeId,
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

				if (searchTags != null && !searchTags.isEmpty()) {
					hasTag = false;
					for (String tag : searchTags) {
						for (AdTag adTag : ad.getAdTags()) {
							if (adTag.getName().equals(tag)) {
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
			return new AdModifView(ad);
		}

		return null;
	}

	@PatchMapping("/modification/tags")
	public int adChangeTags(@RequestBody List<String> tags, @RequestParam int idAd) {
		return adModif.changeAdTags(tags, idAd);
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
	public int adModification(@RequestBody AdModificationService.ModifBody modifBody, @RequestParam int idAd, @RequestParam int modifType) {
		switch (modifType) {
			case ModifType.TITLE -> { return adModif.changeTitle(modifBody.value.toString(), idAd); }
			case ModifType.REFERENCE -> { return adModif.changeReference(modifBody.value.toString(), idAd); }
			case ModifType.PRICE -> { return adModif.changePrice(Double.parseDouble(modifBody.value.toString()), idAd); }
			case ModifType.AD_TYPE -> { return adModif.changeAdType(modifBody.value, idAd); }
			case ModifType.ADDRESS -> { return adModif.changeAddress(modifBody.value.toString(), idAd); }
			case ModifType.IS_SOLD -> { System.out.println(Boolean.valueOf(modifBody.value.toString())); return adModif.changeIsSold(Boolean.parseBoolean(modifBody.value.toString()), idAd); }
			case ModifType.DESCRIPTION -> { return adModif.changeDescription(modifBody.value.toString(), idAd); }
			case ModifType.VISIBILITY -> { return adModif.changeVisibility(Integer.parseInt(modifBody.value.toString()), idAd); }
			case ModifType.SHAPE -> { return adModif.changeShape(Integer.parseInt(modifBody.value.toString()), idAd); }
			default -> { return HtmlCode.BAD_REQUEST; }
		}
	}

	// ONLY FOR THE AD OWNER NOT FOR THE NORMAL USER
	@GetMapping("/get-ad-preview-for-customer/{idAd}")
	public AdBuyerView getUserPreviewAd(@PathVariable int idAd) {
		return new AdBuyerView(adRepo.findOneByIdAdAndIsDeletedFalse(idAd));
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
	public AdCreationFeedback createAd(@RequestBody AdCreationData data) {
		return adService.saveAd(data);
	}

	@PostMapping("/save-ad-images")
	public boolean saveAdImages(@RequestBody List<MultipartFile> adImages,
								@RequestParam int idAd,
								@RequestParam boolean isModif,
								@RequestParam(required = false) String idsToDelete) {
		try {
			// Verify that the adImages are good.
			if(adImages == null) throw new Exception("adImages cannot be null");
			if(adImages.isEmpty()) throw new Exception("adImages need to contain at least one images");

			// Get the ad.
			Ad ad = adRepo.findOneByIdAdAndIsDeletedFalse(idAd);
			if(ad == null) throw new Exception("idAd not found");

			// Save Files
			List<String> filePaths = FileUploadService.saveFiles(adImages, FileType.AD_IMAGE, root);
			if(filePaths == null) throw new Exception("No files was saved.");
			System.out.println(filePaths);

			int spot = 0;
			List<AdImage> adPictures = new ArrayList<>();
			if(isModif) {
				// NEED TO REDO SPOT FOR MODIF BECAUSE WHEN DELETE THIS IS INVALID
				// Spot is equal to the last spot + 1
				spot = ad.getAdImages().getLast().getSpot() + 1;

				// Add back all the old images
				adPictures.addAll(ad.getAdImages());

				// Remove the image that the user removed in frontend
				String[] imgToDelete = idsToDelete.split(",");
				System.out.println(Arrays.asList(imgToDelete));
				System.out.println("Before : "+adPictures);

				for (String idAdImage : imgToDelete) {
					adPictures.removeIf(img -> img.getIdAdImage() == Integer.parseInt(idAdImage));
				}
			}

			for(String path : filePaths) {
				adPictures.add(adImageRepo.save(new AdImage(path, spot, true, ad)));
				spot++;
			}

			ad.setAdImages(adPictures);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
