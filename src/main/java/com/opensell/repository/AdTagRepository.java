package com.opensell.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.opensell.model.ad.AdTag;

@Repository
public interface AdTagRepository extends JpaRepository<AdTag, Integer>{
	AdTag findOneByName(String name);
}