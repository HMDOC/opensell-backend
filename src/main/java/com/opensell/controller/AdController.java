package com.opensell.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.opensell.entities.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.opensell.entities.Ad;
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
		} else return null;
	
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	/*
	 * Filters:
	 * Prix 
	 * Adresse la plus proche (pas sur)
	 * Date d’ajouts 
	 * Catégorie 
	 * Tags générés par l’utilisateur (#BMW, #Benz) 
	 * Condition (usé, neuf) 
		for the required parameter : https://www.baeldung.com/spring-request-param
		this is still unfinished!!!
	 */
	@GetMapping("/search")
	public List<AdSearchPreview> adSearchPreview(@RequestParam(value="q", required=true) String searchQuery,
			@RequestParam(value="p1", required=false) Double priceMin,
			@RequestParam(value="p2", required=false) Double priceMax,
			@RequestParam(value="d1", required=false) Date dateMin,
			@RequestParam(value="d2", required=false) Date dateMax,
			@RequestParam(value="c", required=false) Integer typeId,
			@RequestParam(value="t", required=false) Set<Integer> tagListId,
			@RequestParam(value="s", required=false) Integer shapeId) {
		// Set a value to parameters if null
		priceMin = (priceMin==null) ? 0 : priceMin; priceMax = (priceMax==null) ? 9999999d : priceMax; 
		dateMin = (dateMin==null) ? new Date(0) : dateMin; dateMax = (dateMax==null) ? new Date(Long.MAX_VALUE) : dateMax; 
		
		List<Ad> adList = adRepo.getAdSearch(searchQuery.toUpperCase(), priceMin, priceMax);
		
		if (adList != null) {
			List<AdSearchPreview> resultList = new ArrayList<>(adList.size());
			
			for(Ad ad : adList) {
				// Shortcuts for variables
				double price = ad.getPrice(); int shape = ad.getShape();
				Date date = ad.getAddedDate(); int type = ad.getAdType().getIdAdType();
				
				// Filter results
				if ( ( (shapeId!=null) && (shape!=shapeId) ) ||
					( (typeId!=null) && (type!=typeId) ) ||
					// if date is before dateMin, return -1. if date is after dateMax, return 1.
					( ( date.compareTo(dateMin)<=0 ) || ( date.compareTo(dateMax)>=0 ) )
					) {
					System.out.println("Failed");
					continue;
				}else {
					System.out.println("Passed");
				}
				
				resultList.add(new AdSearchPreview(ad.getTitle(), price, shape, ad.isSold(), 
						ad.getLink(), ad.getAdImages().get(0).getPath()));
			}
			System.out.println(resultList.size());
			return resultList;
		}else {
			return null;
		}
	}
}
