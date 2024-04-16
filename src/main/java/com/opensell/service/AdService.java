package com.opensell.service;

import java.util.*;

import com.opensell.entities.dto.adCreation.AdCreationData;
import com.opensell.entities.dto.adCreation.AdCreationFeedback;
import com.opensell.entities.verification.HtmlCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.opensell.entities.ad.AdTag;
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
			return new AdBuyerView(adRepo.getAdByLink(link));
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
}
