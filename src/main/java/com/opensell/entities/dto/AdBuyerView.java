package com.opensell.entities.dto;

import java.sql.Date;
import java.util.List;
import java.util.Set;

/**
 * Record that contain the essential data that a customer need to see
 * when clicking on ad.
 * 
 * @author Achraf
 */
public record AdBuyerView(String adTitle, 
						  double adPrice,
						  Date adAddedDate, 
						  int adShape,
						  boolean isAdSold,
						  int adVisibility,
						  String adDescription,
						  String adAddress,
						  String adTypeName,
						  Set<String> adTagsName,
						  List<String> adImagesPath,
						  String username,
						  String userLink,
						  String userIcon) {}