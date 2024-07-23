package com.opensell.ad.catalog;


import com.opensell.model.Ad;
import lombok.Data;

/**
 * Record used for when the customer will see an Ad while searching.
 * Like a preview of it
 *
 * @author Davide
 */

// Maybe the order
@Data
public class AdSearchPreview {
	public int id;
	public String adTitle;
	public double adPrice;
	public int adShape;
	public boolean isAdSold;
	public String adFirstImagePath;

	public AdSearchPreview(Ad ad) {
		this.adTitle = ad.getTitle();
		this.adPrice = ad.getPrice();
		this.adShape = ad.getShape();
		this.isAdSold = ad.isSold();
		this.id = ad.getIdAd();
		this.adFirstImagePath = ad.getFirstImagePath();
	}
}
