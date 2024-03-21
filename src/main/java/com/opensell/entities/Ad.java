package com.opensell.entities;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import com.opensell.entities.ad.AdImage;
import com.opensell.entities.ad.AdTag;
import com.opensell.entities.ad.AdType;

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
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Column(nullable = false)
    private String reference;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false, unique = true)
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

	@OneToMany
	@JoinColumn(name = "ad_id", nullable = false)
	private List<AdImage> adImages;

	@ManyToOne
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;
}