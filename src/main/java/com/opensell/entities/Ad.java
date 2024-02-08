package com.opensell.entities;

import java.sql.Date;
import com.opensell.entities.ad.AdType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(uniqueConstraints = {
	@UniqueConstraint(columnNames = {"reference", "idAd"}),
	@UniqueConstraint(columnNames = {"title", "idAd"})
})
@Entity @Data @AllArgsConstructor @NoArgsConstructor
public class Ad {
    @Id private int idAd;
    
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private float price;

    @Column(nullable = false, columnDefinition = "DATETIME DEFAULT NOW()")
    private Date addedDate;

    @OneToOne
    @JoinColumn(name = "ad_type_id", nullable = false)
    private AdType adType;

    @Column(nullable = false)
    private int adCondition;

    @Column(nullable = false)
    private String description;
    
    @Column(nullable = false)
    private String reference;
}