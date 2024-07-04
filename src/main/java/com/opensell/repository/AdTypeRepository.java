package com.opensell.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.opensell.model.ad.AdType;

public interface AdTypeRepository extends JpaRepository<AdType, Integer> {
    public AdType findOneByName(String name);

    public AdType findOneByIdAdType(int idAdType);

    public List<AdType> findAll();
}
