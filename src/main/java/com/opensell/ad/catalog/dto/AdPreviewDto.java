package com.opensell.ad.catalog.dto;

import com.opensell.model.Ad;

/**
 * Record used for when the customer will see an Ad while searching.
 * Like a preview of it
 *
 * @author Davide
 */
public record AdPreviewDto(
	String id,
	String title,
	double price,
	String firstImage,
	boolean sold,
	int visibility
) {
	public AdPreviewDto(Ad ad) {
		this(
			ad.getId(),
			ad.getTitle(),
			ad.getPrice(),
			ad.getFirstImage(),
			ad.isSold(),
			ad.getVisibility()
		);
	}
}
