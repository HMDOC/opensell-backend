package com.opensell.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.opensell.entities.Ad;

@Repository
public interface AdRepository extends JpaRepository<Ad, Integer> {
	// if the ad is not deleted and not private
	@Query("SELECT a FROM Ad a WHERE a.link = ?1 AND a.isDeleted = false AND a.visibility != 1")
	public Ad getAdByLink(String link);
	
	 // https://www.baeldung.com/spring-jpa-like-queries
	@Query("SELECT a FROM Ad a "
			+ "WHERE ( a.isDeleted = false AND a.visibility != 1 AND "
			+ "( UPPER(a.title) LIKE %?1% OR UPPER(a.description) LIKE %?1% ) AND "
			+ "( a.price >= ?2 AND a.price <= ?3 ) ) "
			+ "ORDER BY a.addedDate DESC")
	public List<Ad> getAdSearch(String searchName, Double priceMin, Double priceMax);
	
}


/*
 "SELECT a.title, a.price, a.shape, a.isSold, a.addedDate, a.link, img.path FROM Ad a, AdImage img "
			+ "img.adId ON a.idAd = img.adId" // Put it in comment because I do not know what you are trying to do but maybe the native one is better.
			+ "WHERE (a.isDeleted = false AND a.visibility != 1 AND (a.title LIKE ?1 OR a.description LIKE ?1)) "
			+ "ORDER BY a.addedDate DESC"
 */