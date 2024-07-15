package com.opensell.model.ad;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class AdSearchParams {
	private String query;
	private Double priceMin = 0.0d;
	private Double priceMax = 99990.0d;
    private Date dateMin = Date.valueOf("2020-01-01");
    private Date dateMax = Date.valueOf("3000-01-01");
    private Integer typeId;
    private List<String> adTags = new ArrayList<String>();
	private Integer shapeId; 
    private Boolean filterSold = null;
    private String sortBy = "addedDate";
    private boolean reverseSort = false;
    
	public AdSearchParams(String query, Double priceMin, Double priceMax, Date dateMin, Date dateMax, Integer typeId,
			List<String> adTags, Integer shapeId, Boolean filterSold, String sortBy, boolean reverseSort) {
		super();
		this.query = query;
		this.priceMin = (priceMin != null) ? priceMin : 0.0d;
		this.priceMax = (priceMax != null) ? priceMax : 99990.0d;
		this.dateMin = (dateMin != null) ? dateMin : Date.valueOf("2020-01-01");
		this.dateMax = (dateMax != null) ? dateMax : Date.valueOf("3000-01-01");
		this.typeId = typeId;
		this.adTags = (adTags != null) ? adTags : new ArrayList<String>();
		this.shapeId = shapeId;
		this.filterSold = filterSold;
		this.sortBy = (sortBy!=null) ? sortBy : "addedDate";
		this.reverseSort = reverseSort;
	}

	public String getQuery() {
		return query;
	}

	public Double getPriceMin() {
		return priceMin;
	}

	public Double getPriceMax() {
		return priceMax;
	}

	public Date getDateMin() {
		return dateMin;
	}

	public Date getDateMax() {
		return dateMax;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public List<String> getAdTags() {
		return adTags;
	}

	public Integer getShapeId() {
		return shapeId;
	}

	public Boolean getFilterSold() {
		return filterSold;
	}

	public String getSortBy() {
		return sortBy;
	}

	public boolean isReverseSort() {
		return reverseSort;
	}

	@Override
	public String toString() {
		return "AdSearchParams [query=" + query + ", priceMin=" + priceMin + ", priceMax=" + priceMax + ", dateMin="
				+ dateMin + ", dateMax=" + dateMax + ", typeId=" + typeId + ", adTags=" + adTags + ", shapeId="
				+ shapeId + ", filterSold=" + filterSold + ", sortBy=" + sortBy + ", reverseSort=" + reverseSort + "]";
	}
	
	
}
