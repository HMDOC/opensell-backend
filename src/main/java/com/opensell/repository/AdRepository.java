package com.opensell.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.opensell.entities.Ad;
import com.opensell.entities.ad.AdVisibility;

@Repository
public interface AdRepository extends JpaRepository<Ad, Integer> {
	// if the ad is not deleted and not private
	@Query("SELECT a FROM Ad a WHERE a.link = ?1 AND a.isDeleted = false AND a.visibility != 1")
	public Ad getAdByLink(String link);
	
}
