package com.opensell.controller;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.opensell.entities.Ad;
import com.opensell.entities.Customer;
import com.opensell.entities.dto.AdBuyerView;
import com.opensell.entities.dto.AdSearchPreview;
import com.opensell.repository.AdRepository;

@CrossOrigin(value = "http://localhost/")
@RestController
@RequestMapping("/ad")
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


	@GetMapping("/search")
	public List<AdSearchPreview> adSearchPreview(@RequestParam String searchQuery) {
		List<Ad> adList = adRepo.getUnfilteredSearch(searchQuery);

		if (adList != null) {
			List<AdSearchPreview> resultList = new ArrayList<>(adList.size());
			for(Ad ad : adList) {
				resultList.add(new AdSearchPreview(ad.getTitle(), ad.getPrice(), ad.getShape(),
						ad.isSold(), ad.getLink(), ad.getAdImages().get(0).getPath()));
			}

			return resultList;
		} else {
			return null;
		}
	}
}
