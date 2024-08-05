package com.opensell.ad.catalog.dto;

public record AdSearchParamsDto(
	String query,
	Double priceMin,
	Double priceMax,
	String typeId,
	String[] tags,
	Integer shape,
	Boolean filterSold,
	String sortBy,
	byte reverseSort
) {
	public AdSearchParamsDto(String query, Double priceMin, Double priceMax, String typeId,
							 String[] tags, Integer shape, Boolean filterSold, String sortBy, byte reverseSort) {
		this.query = query != null ? query.trim() : "";
		this.priceMin = (priceMin != null) ? priceMin : 0.0d;
		this.priceMax = (priceMax != null) ? priceMax : 99990.0d;
		this.typeId = typeId;
		this.tags = tags != null ? tags : new String[0];
		this.shape = shape;
		this.filterSold = filterSold;
		this.sortBy = (sortBy != null) ? sortBy : "addedDate";
		this.reverseSort = reverseSort;
	}
}
