package com.opensell.ad.catalog.dto;

import com.opensell.model.Ad;
import lombok.Data;

/**
 * Record used for when the customer will see an Ad while searching.
 * Like a preview of it
 *
 * @author Davide
 */
@Data
public class AdPreviewDto {
	public int id;
	public String adTitle;
	public double adPrice;
	public int adShape;
	public boolean isAdSold;
	public String adFirstImagePath;

	public AdPreviewDto(Ad ad) {
		this.adTitle = ad.getTitle();
		this.adPrice = ad.getPrice();
		this.adShape = ad.getShape();
		this.isAdSold = ad.isSold();
		this.id = ad.getIdAd();
		this.adFirstImagePath = ad.getFirstImagePath();
	}
}
