package com.opensell.entities.dto;

import com.opensell.entities.Ad;

public class DisplayAdView {
    public int idAd;
    public String title;
    public String description;
    public Double price;
    public String firstImage;
    public String link;
    public Boolean isSold;
    public Integer visibility;

    public DisplayAdView(Ad ad) {
        if (ad != null) {
            this.idAd = ad.getIdAd();
            this.title = ad.getTitle();
            this.description = ad.getDescription();
            this.price = ad.getPrice();
            this.firstImage = ad.getFirstImagePath();
            this.link = ad.getLink();
            this.isSold = ad.isSold();
            this.visibility = ad.getVisibility();
        }
    }
}