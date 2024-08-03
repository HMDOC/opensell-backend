package com.opensell.repository;

import com.opensell.model.ad.AdType;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdTypeRepository extends MongoRepository<AdType, String> {
    AdType findOneById(String id);
}
