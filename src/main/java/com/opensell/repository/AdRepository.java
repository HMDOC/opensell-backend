package com.opensell.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.opensell.entities.Ad;

@Repository
public interface AdRepository extends JpaRepository<Ad, Integer> {
	public Ad findByLink(String link);
}
