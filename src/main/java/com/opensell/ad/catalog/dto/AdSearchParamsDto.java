package com.opensell.ad.catalog.dto;

import java.time.LocalDateTime;
import java.util.List;

public record AdSearchParamsDto(
	String query,
	Double priceMin,
	Double priceMax,
	LocalDateTime dateMin,
	LocalDateTime dateMax,
	String typeId,
	List<String> tags,
	Integer shape,
	Boolean filterSold,
	String sortBy,
	byte reverseSort
) {
	public AdSearchParamsDto(String query, Double priceMin, Double priceMax, LocalDateTime dateMin, LocalDateTime dateMax, String typeId,
							 List<String> tags, Integer shape, Boolean filterSold, String sortBy, byte reverseSort) {
		this.query = query != null ? query.trim() : "";
		this.priceMin = (priceMin != null) ? priceMin : 0.0d;
		this.priceMax = (priceMax != null) ? priceMax : 99990.0d;
		this.dateMin = (dateMin != null) ? dateMin : LocalDateTime.parse("2020-01-01T01:59:15.056");
		this.dateMax = (dateMax != null) ? dateMax : LocalDateTime.parse("3000-01-01T01:59:15.056");
		this.typeId = typeId;
		this.tags = (tags != null) ? tags : List.of();
		this.shape = shape;
		this.filterSold = filterSold;
		this.sortBy = (sortBy != null) ? sortBy : "addedDate";
		this.reverseSort = reverseSort;
	}
}
