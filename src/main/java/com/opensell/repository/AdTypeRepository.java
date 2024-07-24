package com.opensell.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.opensell.model.ad.AdType;

public interface AdTypeRepository extends JpaRepository<AdType, Integer> {
    AdType findOneById(int id);
}
