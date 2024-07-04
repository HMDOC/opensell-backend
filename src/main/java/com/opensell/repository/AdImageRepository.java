package com.opensell.repository;

import com.opensell.model.ad.AdImage;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

@Transactional
public interface AdImageRepository extends JpaRepository<AdImage, Integer> {
    int deleteAllByAdIdAd(Integer idAd);
}
