package com.opensell.model.dto;

import com.opensell.model.Ad;

public class DisplayAdView {
    public int idAd;
    public String title;
    public Double price;
    public String firstImage;
    public String link;
    public Boolean isSold;
    public Integer visibility;

    public DisplayAdView(Ad ad) {
        if (ad != null) {
            this.idAd = ad.getIdAd();
            this.title = ad.getTitle();
            this.price = ad.getPrice();
            this.firstImage = ad.getFirstImagePath();
            this.link = ad.getLink();
            this.isSold = ad.isSold();
            this.visibility = ad.getVisibility();
        }
    }
}