package com.opensell.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.opensell.entities.Ad;

import jakarta.transaction.Transactional;

@Transactional
@Repository
public interface AdRepository extends JpaRepository<Ad, Integer> {
	/**
	 * Return a ad by the link if it is not deleted and not private.
	 * @author Achraf
	 */
	@Query("SELECT a FROM Ad a WHERE a.link = ?1 AND a.isDeleted = false AND a.visibility != 1")
	public Ad getAdByLink(String link);

	 // https://www.baeldung.com/spring-jpa-like-queries
	@Query("SELECT a FROM Ad a "
			+ "WHERE ( a.isDeleted = false AND a.visibility != 1 AND "
			+ "( UPPER(a.title) LIKE %:search% OR UPPER(a.description) LIKE %:search% ) AND "
			+ "( a.price >= :pMin AND a.price <= :pMax ) AND "
			+ "( a.addedDate > :dMin AND a.addedDate < :dMax ) AND "
			+ "( a.shape = :shape OR :shape is null ) AND"
			+ "( a.adType.idAdType = :type OR :type is null ) ) "
			+ "ORDER BY a.addedDate DESC LIMIT :limit")
	public List<Ad> getAdSearch(@Param("search") String searchName, @Param("pMin") Double priceMin,
			@Param("pMax") Double priceMax, @Param("dMin") Date dateMin, @Param("dMax") Date dateMax,
			@Param("shape") Integer shapeId, @Param("type") Integer typeId, @Param("limit") Integer limitNb);

}


/*
 "SELECT a.title, a.price, a.shape, a.isSold, a.addedDate, a.link, img.path FROM Ad a, AdImage img "
			+ "img.adId ON a.idAd = img.adId" // Put it in comment because I do not know what you are trying to do but maybe the native one is better.
			+ "WHERE (a.isDeleted = false AND a.visibility != 1 AND (a.title LIKE ?1 OR a.description LIKE ?1)) "
			+ "ORDER BY a.addedDate DESC"
 */