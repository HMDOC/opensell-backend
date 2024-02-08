package com.opensell.entities.ad;

import com.opensell.entities.Ad;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Data @AllArgsConstructor @NoArgsConstructor
public class AdImage {
	@Id private int idAdImage;
	private String path;
	
	@ManyToOne
	@JoinColumn(name = "ad_id", nullable = false)
	private Ad ad;
}
