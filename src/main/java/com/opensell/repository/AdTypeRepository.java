package com.opensell.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.opensell.entities.ad.AdType;

public interface AdTypeRepository extends JpaRepository<AdType, Integer> {
    public AdType findOneByName(String name);
}
