package com.opensell.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
	
	@Modifying
	@Query(value = "UPDATE ad a SET a.title = ?2 WHERE a.id_ad = ?1 AND is_deleted = 0 AND is_sold = 1 LIMIT 1", nativeQuery = true)
	public int changeAdTitle(int idAd, String title);
	
}
