package com.opensell.entities.dto;


/**
 * Record used for when the user will see an Ad while searching. 
 * Like a preview of it
 * 
 * @author Davide
 */

public record AdSearchPreview(
			String adTitle,
			double adPrice,
			int adShape,
			boolean isAdSold,
			String adLink,
			String adFirstImagePath) {}