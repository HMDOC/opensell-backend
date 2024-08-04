package com.opensell.ad.catalog.dto;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public record AdSearchParamsDto(
	String query,
	Double priceMin,
	Double priceMax,
	LocalDateTime dateMin,
	LocalDateTime dateMax,
	Integer typeId,
	List<String> tags,
	String shapeId,
	Boolean filterSold,
	String sortBy,
	byte reverseSort
) {
	public AdSearchParamsDto(String query, Double priceMin, Double priceMax, LocalDateTime dateMin, LocalDateTime dateMax, Integer typeId,
							 List<String> tags, String shapeId, Boolean filterSold, String sortBy, byte reverseSort) {
		this.query = query;
		this.priceMin = (priceMin != null) ? priceMin : 0.0d;
		this.priceMax = (priceMax != null) ? priceMax : 99990.0d;
		this.dateMin = (dateMin != null) ? dateMin : LocalDateTime.of(2020, Month.JANUARY, 1, 1, 59);
		this.dateMax = (dateMax != null) ? dateMax : LocalDateTime.of(3000, Month.JANUARY, 1, 1, 59);
		this.typeId = typeId;
		this.tags = (tags != null) ? tags : new ArrayList<String>();
		this.shapeId = shapeId;
		this.filterSold = filterSold;
		this.sortBy = (sortBy != null) ? sortBy : "addedDate";
		this.reverseSort = reverseSort;
	}
}
