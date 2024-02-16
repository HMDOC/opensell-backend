package com.opensell.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
	
	 // Unfinished. https://www.baeldung.com/spring-jpa-like-queries
		@Query("SELECT a FROM Ad a "
				+ "WHERE (a.isDeleted = false AND a.visibility != 1 AND (a.title LIKE %?1% OR a.description LIKE %?1% )) "
				+ "ORDER BY a.addedDate DESC")
		public List<Ad> getUnfilteredSearch(String searchName);
	
	/*
	 // Unfinished. https://www.baeldung.com/spring-jpa-like-queries
	@Query("SELECT new com.opensell.entities.dto.AdSearchPreview(a.title, a.price, a.shape, a.isSold, a.link, '') FROM Ad a "
			+ "WHERE (a.isDeleted = false AND a.visibility != 1 AND (a.title LIKE %:search% OR a.description LIKE %:search%)) "
			+ "ORDER BY a.addedDate DESC")
	public List<AdSearchPreview> getUnfilteredSearch(@Param("search") String searchName);
	 
	 */
	/*
	 * Filters:
	 * Prix 
	 * Adresse la plus proche (pas sur)
	 * Date d’ajouts 
	 * Couleur 
	 * Catégorie 
	 * Tags générés par l’utilisateur (#BMW, #Benz) 
	 * Condition (usé, neuf) 
	 */
}


/*
 "SELECT a.title, a.price, a.shape, a.isSold, a.addedDate, a.link, img.path FROM Ad a, AdImage img "
			+ "img.adId ON a.idAd = img.adId" // Put it in comment because I do not know what you are trying to do but maybe the native one is better.
			+ "WHERE (a.isDeleted = false AND a.visibility != 1 AND (a.title LIKE ?1 OR a.description LIKE ?1)) "
			+ "ORDER BY a.addedDate DESC"
 */