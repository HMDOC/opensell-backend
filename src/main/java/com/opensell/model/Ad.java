package com.opensell.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.opensell.model.ad.AdImage;
import com.opensell.model.ad.AdTag;
import com.opensell.model.ad.AdType;
import com.opensell.ad.catalog.AdSearchPreview;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Table(uniqueConstraints = {
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

    @Column(nullable = false)
    private String address;

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
	private List<AdImage> adImages = new ArrayList<>();

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