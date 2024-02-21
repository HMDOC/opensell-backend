package com.opensell.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.opensell.entities.ad.AdTag;

public interface AdTagRepository extends JpaRepository<AdTag, Integer>{
	public AdTag findByName(String name);
}