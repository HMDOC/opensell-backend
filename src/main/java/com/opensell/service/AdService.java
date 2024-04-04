package com.opensell.service;

import java.util.*;

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

	@Autowired
	private LinkGenerator linkGenerator;

	/**
	 * To get an AdBuyer from a link.
	 *
	 * @author Achraf
	 */
	public AdBuyerView getAdBuyerView(String link) {
		try {
			Ad ad = adRepo.getAdByLink(link);

			if (ad != null) {
				return new AdBuyerView(ad);
			} else {
				return null;
			}

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
		try {
			int result = 0;
			int currentAdId;
			LinkedHashSet<Integer> tagSet = new LinkedHashSet<>(data.tagIds().length);
			for (int elem : data.tagIds()) tagSet.add(elem);

			if (adRepo.checkTitle(data.customerId(), data.title()) == 0) {
				result = result + adRepo.createAd(data.adTypeId(),
						data.customerId(), data.price(),
						data.shape(), data.visibility(),
						data.title(), data.description(),
						data.address(),
						linkGenerator.generateAdLink(),
						data.reference());
				currentAdId = adRepo.getAdIdFromTitleAndCustomerID(data.customerId(), data.title());
				for (AdCreationImageData recordData : data.imageData()) result = result + adRepo.saveAdImage(currentAdId, recordData.spot(), recordData.path());
				for (Integer tagId : tagSet) result = result + adRepo.saveRelAdTag(currentAdId, tagId);
			} else throw new Exception("titre existe déjà...");
			return new AdCreationFeedback(HtmlCode.SUCCESS, result, null);
		} catch (Exception e) {
			return new AdCreationFeedback(HtmlCode.UNIQUE_FAILED, 0, e.getMessage());
		}
	}
}
