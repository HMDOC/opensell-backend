package com.opensell.ad.catalog.dto;

import com.opensell.model.Ad;
import com.opensell.model.AdCategory;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Record that contain the essential data that a customer need to see
 * when clicking on ad.
 *
 * @author Achraf
 */
public record AdViewDto(
	String adTitle,
	double adPrice,
	LocalDateTime adAddedDate,
	int adShape,
	boolean isAdSold,
	int adVisibility,
	String adDescription,
	String adAddress,
	AdCategory adCategory,
	Set<String> adTagsName,
	Set<String> adImages,
	String username,
	String userIcon
) {
	public AdViewDto(Ad ad) {
		this(
			ad.getTitle(),
			ad.getPrice(),
			ad.getAddedDate(),
			ad.getShape(),
			ad.isSold(),
			ad.getVisibility(),
			ad.getDescription(),
			ad.getAddress(),
			ad.getAdCategory(),
			ad.getTags(),
			ad.getImages(),
			ad.getCustomer().getUsername(),
			ad.getCustomer().getIconPath()
		);
	}
}