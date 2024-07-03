package com.opensell.entities.dto;

import com.opensell.entities.Ad;
import com.opensell.entities.ad.AdImage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Date;
import java.util.List;
import java.util.Set;

/**
 * Record that contain the essential data when a user is trying to UPDATE an Ad.
 *
 * @author Achraf
 */
@Deprecated(forRemoval = true)
@ToString
public class AdModifView extends AdBuyerView {
	public int idAd;
	public String reference;
	public String link;

	public AdModifView(Ad ad) {
		super(ad);
		this.idAd = ad.getIdAd();
		this.reference = ad.getReference();
		this.link = ad.getLink();
	}
}