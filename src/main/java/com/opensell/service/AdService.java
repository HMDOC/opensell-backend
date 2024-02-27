package com.opensell.service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.opensell.entities.Ad;
import com.opensell.entities.Customer;
import com.opensell.entities.dto.AdBuyerView;
import com.opensell.entities.verification.ChangeInEntity;
import com.opensell.entities.verification.VerifyCode;
import com.opensell.repository.AdRepository;

@Service
public class AdService {
	@Autowired
	AdRepository adRepo;

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
	 * To change the title of an Ad.
	 *
	 * @author Achraf
	 */
	public VerifyCode changeTitle(ChangeInEntity<String> changeInEntity, String title, int idCustomer) throws Exception {
		return ChangeInEntity.checkModifError(changeInEntity, title,
				() -> title.length() <= 255 && title.length() > 0 && adRepo.checkTitle(idCustomer, title) == 0);
	}

	/**
	 * To change the reference of an Ad.
	 *
	 * @author Achraf
	 */
	public VerifyCode changeReference(ChangeInEntity<String> changeInEntity, String reference, int idCustomer) throws Exception {
		return ChangeInEntity.checkModifError(changeInEntity, reference,
				() -> reference.length() <= 255 && reference.length() > 0 && adRepo.checkReference(idCustomer, reference) == 0);
	}
}
