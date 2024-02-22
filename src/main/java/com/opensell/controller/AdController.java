package com.opensell.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.opensell.entities.Customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
		for the defaultValue parameter : https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/RequestParam.html
		this is still unfinished!!!
	 */
	@GetMapping("/search")
	public List<AdSearchPreview> adSearch(@RequestParam(required=true) String query,
			@RequestParam(required=false, defaultValue="0") Double priceMin,
			@RequestParam(required=false, defaultValue="9999999d") Double priceMax,
			@RequestParam(required=false, defaultValue="2020-01-01") Date dateMin,
			@RequestParam(required=false, defaultValue="3000-01-01") Date dateMax,
			@RequestParam(required=false) Integer typeId,
			@RequestParam(required=false) Set<Integer> tagListId,
			@RequestParam(required=false) Integer shapeId,
			@RequestParam(required=false, defaultValue="16") Integer limit,
			@RequestParam(required=false, defaultValue="addedDate") String sortBy) {
		
		List<Ad> adList = adRepo.getAdSearch(query.toUpperCase(), priceMin, priceMax, dateMin, dateMax, shapeId, typeId, 
				limit, tagListId, Sort.by(sortBy));
		
		if (adList != null) {
			List<AdSearchPreview> resultList = new ArrayList<>(adList.size());
			
			for(Ad ad : adList) {
				// Shortcuts for variables
				double price = ad.getPrice(); int shape = ad.getShape();
				Date date = ad.getAddedDate(); int type = ad.getAdType().getIdAdType();
				
				/*
				// Filter results
				if ( false ) {
					System.out.println("Failed");
					continue;
				}else {
					System.out.println(date);
					System.out.println("Passed");
				}
				*/
				
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
