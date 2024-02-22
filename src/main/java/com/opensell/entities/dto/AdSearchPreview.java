package com.opensell.entities.dto;


/**
 * Record used for when the customer will see an Ad while searching.
 * Like a preview of it
 *
 * @author Davide
 */

// Maybe the order
public record AdSearchPreview(
			String adTitle,
			double adPrice,
			int adShape,
			boolean isAdSold,
			String adLink,
			String adFirstImagePath) {}