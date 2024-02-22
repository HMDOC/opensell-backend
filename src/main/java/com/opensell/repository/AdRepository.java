package com.opensell.repository;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.opensell.entities.Ad;

@Repository
public interface AdRepository extends JpaRepository<Ad, Integer> {
	// if the ad is not deleted and not private
	@Query("SELECT a FROM Ad a WHERE a.link = ?1 AND a.isDeleted = false AND a.visibility != 1")
	public Ad getAdByLink(String link);
	
	// https://www.baeldung.com/spring-jpa-like-queries
	// https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html
	@Query("SELECT a FROM Ad a "
			+ "WHERE ( a.isDeleted = false AND a.visibility != 1 AND "
			+ "( UPPER(a.title) LIKE %:search% OR UPPER(a.description) LIKE %:search% ) AND "
			+ "( a.price between :pMin And :pMax ) AND "
			+ "( a.addedDate between :dMin AND :dMax ) AND "
			+ "( a.shape = :shapeId AND :shapeId not null ) AND"
			+ "( a.adType.idAdType = :typeId AND :typeId not null ) "
			+ "( :tags in a.adTags.idAdTag ) ) "
			+ "LIMIT :limit")
	public List<Ad> getAdSearch(@Param("search") String searchName, @Param("pMin") Double priceMin,@Param("pMax") Double priceMax,
			@Param("dMin") Date dateMin, @Param("dMax") Date dateMax, @Param("shapeId") Integer shapeId, 
			@Param("typeId") Integer typeId, @Param("limit") Integer limitNb, @Param("tags") Set<Integer> tags, @Param("sort") Sort sort);
	
}


/*
 "SELECT a.title, a.price, a.shape, a.isSold, a.addedDate, a.link, img.path FROM Ad a, AdImage img "
			+ "img.adId ON a.idAd = img.adId" // Put it in comment because I do not know what you are trying to do but maybe the native one is better.
			+ "WHERE (a.isDeleted = false AND a.visibility != 1 AND (a.title LIKE ?1 OR a.description LIKE ?1)) "
			+ "ORDER BY a.addedDate DESC"
 */