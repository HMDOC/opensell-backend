package com.opensell.ad.catalog.dto;

import com.opensell.model.Ad;
import com.opensell.model.ad.AdType;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Record that contain the essential data that a customer need to see
 * when clicking on ad.
 *
 * @author Achraf
 */
@Data @NoArgsConstructor
public class AdViewDto {
	public String adTitle;
	public double adPrice;
	public LocalDateTime adAddedDate;
	public int adShape;
	public boolean isAdSold;
	public int adVisibility;
	public String adDescription;
	public String adAddress;
	public AdType adType;
	public Set<String> adTagsName;
	public Set<String> adImages;
	public String username;
	public String userIcon;

	public AdViewDto(Ad ad) {
		if(ad != null) {
			var customer = ad.getCustomer();

			this.adTitle = ad.getTitle();
			this.adPrice = ad.getPrice();
			this.adAddedDate = ad.getAddedDate();
			this.adShape = ad.getShape();
			this.isAdSold = ad.isSold();
			this.adVisibility = ad.getVisibility();
			this.adDescription = ad.getDescription();
			this.adAddress = ad.getAddress();
			this.adType = ad.getAdType();
			this.adTagsName = ad.getTags();
			this.adImages = ad.getImages();
			this.username = customer.getUsername();
			this.userIcon = customer.getIconPath();
		}
	}
}