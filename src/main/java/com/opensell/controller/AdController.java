package com.opensell.controller;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.opensell.entities.Ad;
import com.opensell.entities.Customer;
import com.opensell.entities.dto.AdBuyerView;
import com.opensell.repository.AdRepository;

@CrossOrigin(value = "http://localhost/")
@RestController
public class AdController {
	@Autowired
	private AdRepository adRepo;

	/**
	 * Call function from AdService to get an AdBuyer from a link.
	 *
	 * @author Achraf
	 */
	@GetMapping("/get-ad-buyer-view/{link}")
	public AdBuyerView adBuyerView(@PathVariable String link) {
		try {
			Ad ad = adRepo.getAdByLink(link);

			if(ad != null) {
				Customer customer = ad.getCustomer();
				List<String> adImagesPath = new ArrayList<>();
				Set<String> adTagsName = new LinkedHashSet<>();

				/* We need to check if it is the best way, to map the list we have in the Entity or to directly
				ask the Table. */
				ad.getAdImages().forEach(image -> adImagesPath.add(image.getPath()));
				ad.getAdTags().forEach(tags -> adTagsName.add(tags.getName()));

				return new AdBuyerView(ad.getTitle(), ad.getPrice(), ad.getAddedDate(),
									   ad.getShape(), ad.isSold(), ad.getVisibility(),
									   ad.getDescription(), ad.getAddress(), ad.getAdType().getName(),
									   adTagsName, adImagesPath,
									   customer.getUsername(), customer.getLink(), customer.getCustomerInfo().getIconPath());
		} else {
				return null;
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	@PatchMapping("/change-title")
	public boolean changeAdTitle(@RequestParam String title, @RequestParam int idAd) {
		adRepo.changeAdTitle(idAd, title);
		return false;
	}
}
