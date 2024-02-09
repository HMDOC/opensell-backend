package com.opensell.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.opensell.entities.Ad;
import org.springframework.stereotype.Repository;

@Repository
public interface AdRepository extends JpaRepository<Ad, Integer> {
	
}
