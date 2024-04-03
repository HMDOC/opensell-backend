package com.opensell.entities.ad;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(uniqueConstraints = {
	@UniqueConstraint(columnNames = {"ad_id", "spot"})
})
@Entity @Data @AllArgsConstructor @NoArgsConstructor
public class AdImage {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idAdImage;
	
    @Column(nullable = false)
	private String path;
	
    @Column(nullable = false)
	private int spot;

	@Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
	public boolean isLocal;

	public AdImage(String path, int spot, boolean isLocal) {
		this.spot = spot;
		this.path = path;
		this.isLocal = isLocal;
	}
}
