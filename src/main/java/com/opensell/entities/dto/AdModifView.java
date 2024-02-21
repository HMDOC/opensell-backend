package com.opensell.entities.dto;

import java.util.List;
import java.util.Set;

/**
 * Record that contain the essential data when a user is trying to UPDATE an Ad.
 *
 * @author Achraf
 */
public record AdModifView(int idAd,
						  String title,
						  Double price,
						  int shape,
						  boolean isSold,
						  int visibility,
						  String description,
						  String reference,
						  String address,
						  String link,
						  String adTypeName,
						  Set<String> adTagsName,
						  List<String> adImagesPath) {}