package com.opensell.service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.opensell.entities.dto.adCreation.AdCreationData;
import com.opensell.entities.dto.adCreation.AdCreationFeedback;
import com.opensell.entities.dto.adCreation.AdCreationImageData;
import com.opensell.entities.verification.HtmlCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.opensell.entities.Ad;
import com.opensell.entities.Customer;
import com.opensell.entities.ad.AdTag;
import com.opensell.entities.ad.AdType;
import com.opensell.entities.dto.AdBuyerView;
import com.opensell.repository.AdRepository;
import com.opensell.repository.AdTagRepository;
import com.opensell.repository.AdTypeRepository;

/**
 * This service is used to
 */
@Service
public class AdService {
	@Autowired
	private AdRepository adRepo;

	@Autowired
	private AdTagRepository adTagRepo;

	@Autowired
	private AdTypeRepository adTypeRepo;

	/**
	 * To get an AdBuyer from a link.
	 *
	 * @author Achraf
	 */
	public AdBuyerView getAdBuyerView(String link) {
		try {
			Ad ad = adRepo.getAdByLink(link);

			if (ad != null) {
				Customer customer = ad.getCustomer();
				List<String> adImagesPath = new ArrayList<>();
				Set<String> adTagsName = new LinkedHashSet<>();

				ad.getAdImages().forEach(image -> adImagesPath.add(image.getPath()));
				ad.getAdTags().forEach(tags -> adTagsName.add(tags.getName()));

				return new AdBuyerView(ad.getTitle(), ad.getPrice(), ad.getAddedDate(), ad.getShape(), ad.isSold(),
						ad.getVisibility(), ad.getDescription(), ad.getAddress(), ad.getAdType().getName(), adTagsName,
						adImagesPath, customer.getUsername(), customer.getLink(),
						customer.getCustomerInfo().getIconPath());
			} else {
				return null;
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	/**
	 * To change the type of an Ad.
	 * 
	 * @author Achraf
	 * @param adTypeName
	 * @param ad
	 */
	public boolean changeAdType(String adTypeName, Ad ad) {
		try {
			if (ad != null && AdType.isNameValid(adTypeName) && !ad.getAdType().getName().equals(adTypeName)) {
				AdType adType = adTypeRepo.findOneByName(adTypeName);

				if (adType != null) {
					ad.setAdType(adType);
				} else {
					ad.setAdType(adTypeRepo.save(new AdType(adTypeName)));
				}

				return true;
			} else
				return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * To change the tags of an Ad.
	 * 
	 * @author Achraf
	 * @param adTypeName
	 * @param ad
	 */
	public boolean changeAdTags(List<String> frontendTags, Ad ad) {
		try {
			if (frontendTags != null && ad != null) {
				List<AdTag> adTags = new ArrayList<>();

				// Map over the set of string
				frontendTags.forEach(tag -> {
					if (AdTag.isNameValid(tag)) {
						// Get the old tag from the database
						AdTag tagTemp = adTagRepo.findOneByName(tag);

						// If the tag already exists
						if (tagTemp != null) adTags.add(tagTemp);

						// If the tag does exists
						else adTags.add(adTagRepo.save(new AdTag(tag)));
					}
				});

				ad.setAdTags(new LinkedHashSet<AdTag>(adTags));
				return true;
			} else
				return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	/**
	 *
	 * @author Olivier Mansuy
	 */
	public AdCreationFeedback saveAd(AdCreationData data) {
		try {
			int adInsertResult = 0;
			int adImageInsertResult = 0;
			int adTagInsertResult = 0;
			int currentAdId = 0;

			if (adRepo.checkTitle(data.customerId(), data.title()) == 0) {
				adInsertResult = adRepo.createAd(data.adTypeId(),
						data.customerId(), data.price(),
						data.shape(), data.visibility(),
						data.title(), data.description(),
						data.address(),
						"fwianmfawnfawonfoawfnawf",
						data.reference());

				for (AdCreationImageData recordData : data.imageData()) {
//					adRepo.saveAdImage(currentAdId, recordData.spot(), recordData.path());
				}

				for (Integer tagId : data.tagIds()) {
//					adRepo.saveRelAdTag(currentAdId, tagId);
				}
			} else throw new Exception("titre existe déjà...");


			//method pour get l'adId de l'Ad qu'on vient de créer

			return new AdCreationFeedback(HtmlCode.SUCCESS, adInsertResult, null);
		} catch (Exception e) {
			return new AdCreationFeedback(HtmlCode.UNIQUE_FAILED, 0, e.getMessage());
		}
	}
}
