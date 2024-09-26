package com.opensell.repository;

import com.opensell.model.AdCategory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdCategoryRepository extends MongoRepository<AdCategory, String> {
    AdCategory findOneById(String id);
}
