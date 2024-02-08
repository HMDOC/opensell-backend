package com.opensell.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.opensell.entities.Ad;

public interface AdRepository extends JpaRepository<Ad, Integer> {
	
}
