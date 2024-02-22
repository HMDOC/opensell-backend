package com.opensell.service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import com.opensell.entities.Ad;
import com.opensell.entities.Customer;
import com.opensell.entities.ad.AdSetAttribut;
import com.opensell.entities.ad.ChangeInEntity;
import com.opensell.entities.dto.AdBuyerView;
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
	public boolean changeTitle(ChangeInEntity<String> adSetAttribut, String title, int idCustomer) {
		if(title != null && adSetAttribut != null && title.length() <= 255 && adRepo.checkTitle(idCustomer, title) == 0) {
			adSetAttribut.setAttribute(title);
			return true;
		}
		
		return false;
	}
	
	/**
	 * To change the reference of an Ad.
	 *
	 * @author Achraf
	 */
	public boolean changeReference(ChangeInEntity<String> adSetReference, String reference) {
		if(reference != null) {
			
		}
			
		return false;
	}
}
