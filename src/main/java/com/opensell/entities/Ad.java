package com.opensell.entities;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.opensell.entities.ad.AdImage;
import com.opensell.entities.ad.AdTag;
import com.opensell.entities.ad.AdType;
import com.opensell.entities.dto.AdCreator;
import com.opensell.entities.dto.AdSearchPreview;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Table(uniqueConstraints = {
	@UniqueConstraint(columnNames = {"reference", "customer_id"}, name = "reference_customer"),
	@UniqueConstraint(columnNames = {"title", "customer_id"}, name = "title_customer")
})
@Entity @Data @AllArgsConstructor @NoArgsConstructor
public class Ad {
    public static final int DESCRIPTION_MAX_LENGTH = 5000;
    public static final int TITLE_MAX_LENGTH = 80;

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private int idAd;

    @Column(nullable = false, length = TITLE_MAX_LENGTH)
    private String title;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false, columnDefinition = "DATETIME DEFAULT NOW()")
    private Date addedDate;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT 0")
    private boolean isSold;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT 0")
    private boolean isDeleted;

    @Column(nullable = false)
    private int visibility;

    @Column(nullable = false)
    private int shape;
    
    @Column(nullable = false, length = DESCRIPTION_MAX_LENGTH)
    private String description;

    //api
    @Column(nullable = true)
    private String reference;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false, unique = true, length=12)
    private String link;

    @ManyToOne
    @JoinColumn(name = "ad_type_id", nullable = false)
    private AdType adType;

    @ManyToMany
    @JoinTable(
    	name = "ad_ad_tag_rel",
        joinColumns = @JoinColumn(name = "ad_id"),
        inverseJoinColumns = @JoinColumn(name = "ad_tag_id")
    )
    private Set<AdTag> adTags;

    @JsonIgnore
	@OneToMany(mappedBy = "ad")
	private List<AdImage> adImages;

	@ManyToOne
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;

    @JsonIgnore
    public Set<String> getTagsName() {
        return this.adTags
                .stream()
                .map(AdTag::getName)
                .collect(Collectors.toSet());
    }

    @JsonIgnore
    public List<String> getImagesPath() {
        return this.adImages
                .stream()
                .map(AdImage::getPath)
                .toList();
    }

    @JsonIgnore
    public String getFirstImagePath() {
        if(this.adImages != null) {
            Optional<String> imagePath = this.adImages
                .stream()
                .filter(img -> img.getSpot() == 0)
                .findFirst()
                .map(AdImage::getPath);

            return imagePath.orElse(null);
        }

        return null;
    }

    @JsonIgnore
    public AdSearchPreview toAdSearchPreview() {
        return new AdSearchPreview(this);
    }
}