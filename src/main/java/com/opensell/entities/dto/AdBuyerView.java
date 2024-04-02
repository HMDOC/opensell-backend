package com.opensell.entities.dto;

import com.opensell.entities.Ad;

import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Record that contain the essential data that a customer need to see
 * when clicking on ad.
 *
 * @author Achraf
 */
public record AdBuyerView(String adTitle,
						  double adPrice,
						  Date adAddedDate,
						  int adShape,
						  boolean isAdSold,
						  int adVisibility,
						  String adDescription,
						  String adAddress,
						  String adTypeName,
						  Set<String> adTagsName,
						  List<String> adImagesPath,
						  String username,
						  String userLink,
						  String userIcon) {
	public static AdBuyerView createFromAd(Ad ad) {
		Set<String> tags = new LinkedHashSet<>();
		List<String> imagesPath = new ArrayList<>();
		var customer = ad.getCustomer();

		ad.getAdTags1().forEach(adTag -> tags.add(adTag.getName()));
		ad.getAdImages().forEach(adImage-> imagesPath.add(adImage.getPath()));

		return new AdBuyerView(
			ad.getTitle(),
			ad.getPrice(),
			ad.getAddedDate(),
			ad.getShape(),
			ad.isSold(),
			ad.getVisibility(),
			ad.getDescription(),
			ad.getAddress(),
			ad.getAdType().getName(),
			tags,
			imagesPath,
			customer.getUsername(),
			customer.getLink(),
			customer.getCustomerInfo().getIconPath()
		);
	}
}