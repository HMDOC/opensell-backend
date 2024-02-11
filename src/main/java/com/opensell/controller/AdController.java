package com.opensell.controller;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.opensell.entities.Ad;
import com.opensell.entities.User;
import com.opensell.entities.dto.AdBuyerView;
import com.opensell.repository.AdRepository;

@RestController
public class AdController {
	@Autowired
	private AdRepository adRepo;
	
	/**
	 * Function to get the Ad information when someone is looking
	 * as a buyer.
	 * 
	 * @author Achraf
	 */
	@GetMapping("/get-ad-buyer-view/{link}")
	public AdBuyerView getMethodName(@PathVariable String link) {
		Ad ad = adRepo.findByLink(link);
		
		if(ad != null) {
			User user = ad.getUser();
			List<String> adImagesPath = new ArrayList<>();
			Set<String> adTagsName = new LinkedHashSet<>();
			
			/* We need to check if it is the best way, to map the list we have in the Entity or to directly 
			ask the Table. */
			ad.getAdImages().forEach(image -> adImagesPath.add(image.getPath()));
			ad.getAdTags().forEach(tags -> adTagsName.add(tags.getName()));
			
			return new AdBuyerView(ad.getTitle(), ad.getPrice(), ad.getAddedDate(), 
									ad.getShape(), ad.isSold(), ad.getVisibility(), 
									ad.getDescription(), ad.getAddress(), ad.getAdType().getName(), 
									adTagsName, adImagesPath, ad.getLink(), 
									user.getUserName(), user.getProfilLink(), user.getUserInfo().getIconPath());
		} else return null;
	}
}
