package com.opensell.entities.dto;

import java.util.Set;

/**
 * Record that contain the essential data when a user is trying to UPDATE a Ad.
 *
 * @author Achraf
 */
public record AdModifView(String title,
						  double price,
						  int shape,
						  boolean isSold,
						  int visibility,
						  String description,
						  String reference,
						  String address,
						  String link,
						  String adTypeName,
						  Set<String> adTagsName,
						  String adImagesPath) {}