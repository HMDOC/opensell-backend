package com.opensell.entities.dto;

import com.opensell.entities.Ad;
import com.opensell.entities.ad.AdImage;
import com.opensell.entities.ad.AdType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Record that contain the essential data that a customer need to see
 * when clicking on ad.
 *
 * @author Achraf
 */
@Data @NoArgsConstructor
public class AdBuyerView {
	public String adTitle;
	public double adPrice;
	public Date adAddedDate;
	public int adShape;
	public boolean isAdSold;
	public int adVisibility;
	public String adDescription;
	public String adAddress;
	public AdType adType;
	public Set<String> adTagsName;
	public List<AdImage> adImages;
	public String username;
	public String userLink;
	public String userIcon;

	public AdBuyerView(Ad ad) {
		if(ad != null) {
			Set<String> tags = new LinkedHashSet<>();
			var customer = ad.getCustomer();

			ad.getAdTags().forEach(adTag -> tags.add(adTag.getName()));
			this.adTitle = ad.getTitle();
			this.adPrice = ad.getPrice();
			this.adAddedDate = ad.getAddedDate();
			this.adShape = ad.getShape();
			this.isAdSold = ad.isSold();
			this.adVisibility = ad.getVisibility();
			this.adDescription = ad.getDescription();
			this.adAddress = ad.getAddress();
			this.adType = ad.getAdType();
			this.adTagsName = tags;
			this.adImages = ad.getAdImages();
			this.username = customer.getUsername();
			this.userLink = customer.getLink();
			this.userIcon = customer.getCustomerInfo().getIconPath();
		}
	}
}