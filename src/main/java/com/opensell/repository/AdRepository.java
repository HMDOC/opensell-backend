package com.opensell.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.opensell.entities.Ad;
import com.opensell.entities.ad.AdTag;

import jakarta.transaction.Transactional;

@Transactional
@Repository
public interface AdRepository extends JpaRepository<Ad, Integer> {
	/**
	 * Return a ad by the link if it is not deleted and not private.
	 * 
	 * Purpose : For AdBuyerView
	 * @author Achraf
	 */
	@Query(value = "SELECT * FROM ad a WHERE a.link = ?1 AND a.is_deleted = false AND a.visibility != 1 LIMIT 1", nativeQuery = true)
	public Ad getAdByLink(String link);
	
	/**
	 * 
	 * @author Davide
	 */
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


	/**
	 * Return an ad that have the idAd in parameter if it is not deleted.
	 *
	 * Purpose : To modify an ad
	 * @author Achraf
	 */
	@Query(value = "SELECT * FROM ad a WHERE a.id_ad = ?1 AND a.is_deleted = false LIMIT 1", nativeQuery = true)
	public Ad getAdByIdAd(int idAd);
	
	/***
	 * To see if a this customer already have an ad with this title.
	 * 
	 * Purpose : To modify an ad
	 * @author Achraf
	 */
	@Query(value = "SELECT EXISTS(SELECT * FROM ad a WHERE a.customer_id = ?1 AND a.title = ?2 LIMIT 1)", nativeQuery = true)
	public byte checkTitle(int idCustomer, String title);
	
	/***
	 * To see if a this customer already have an ad with this reference.
	 * 
	 * Purpose : To modify an ad
	 * @author Achraf
	 */
	@Query(value = "SELECT EXISTS(SELECT * FROM ad a WHERE a.customer_id = ?1 AND a.reference = ?2 LIMIT 1)", nativeQuery = true)
	public byte checkReference(int idCustomer, String reference);
}


/*
 "SELECT a.title, a.price, a.shape, a.isSold, a.addedDate, a.link, img.path FROM Ad a, AdImage img "
			+ "img.adId ON a.idAd = img.adId" // Put it in comment because I do not know what you are trying to do but maybe the native one is better.
			+ "WHERE (a.isDeleted = false AND a.visibility != 1 AND (a.title LIKE ?1 OR a.description LIKE ?1)) "
			+ "ORDER BY a.addedDate DESC"
 */