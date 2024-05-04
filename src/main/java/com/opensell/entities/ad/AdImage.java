package com.opensell.entities.ad;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.opensell.entities.Ad;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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

	@JsonIgnore
	@ToString.Exclude
	@ManyToOne
	@JoinColumn(name = "ad_id", nullable = false)
	public Ad ad;

	public AdImage(String path, int spot, boolean isLocal, Ad ad) {
		this.spot = spot;
		this.path = path;
		this.isLocal = isLocal;
		this.ad = ad;
	}
}
