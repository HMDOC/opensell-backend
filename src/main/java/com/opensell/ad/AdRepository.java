package com.opensell.ad;

import com.opensell.ad.listings.dto.AdPreviewProjectionDto;
import com.opensell.model.Ad;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AdRepository extends MongoRepository<Ad, String> {
    /**
     * REDO
     * @author Davide, Achraf
     */
    // https://www.baeldung.com/spring-jpa-like-queries
    // https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html
    /*@Query("""
        SELECT a FROM Ad a \
        WHERE ( a.deleted = false AND a.visibility = 0 AND \
        ( UPPER(a.title) LIKE %:search% OR UPPER(a.description) LIKE %:search% ) AND \
        ( a.price between :pMin And :pMax ) AND \
        ( a.addedDate between :dMin AND :dMax ) AND \
        ( a.shape = :shapeId OR :shapeId is null ) AND\
        ( a.adType.id = :typeId OR :typeId is null ) AND \
        ( a.sold = :filterSold OR :filterSold is null) )\
        """)*/
    // Need to finish with sold.
    @Query("""
    {
        deleted: false,
        visibility: 0,
        $or: [{title: {$regex: '?0', $options: 'i'}}, {description: {$regex: '?0', $options: 'i'}}],
        price: {$gte: ?1, $lte: ?2},
        addedDate: {$gte: ?3, $lte: ?4},
        shape: ?5,
        'adType.id': ?6
    }
    """)
    List<Ad> getAdSearch(String searchName, Double priceMin, Double priceMax,
                         LocalDateTime dateMin, LocalDateTime dateMax, String shapeId,
                         Integer typeId, Boolean filterSold, Sort sort);

    /**
     * Return an ad that have the idAd in parameter if it is not deleted.
     * <p>
     * Purpose : To modify an ad
     *
     * @author Achraf
     */
    @Query("{deleted: false, id: ?0}")
    Ad getAdToModify(String id);

    Ad findOneByIdAndDeletedFalse(String idAd);

    Optional<Ad> findOneByTitleAndCustomerIdAndDeletedFalse(String title, String customerId);

    @Query("{deleted: false, id: ?0}")
    @Update("{$set: {'deleted' : true}}")
    int hideAd(String id);

    /**
     * This is the function to get the Ad for my-ads page.
     */
    @Query("{'customer.id': ?0, deleted: false}")
    List<AdPreviewProjectionDto> getCustomerAds(String customerId);

    /**
     * This is the function to get the Ad for the user profile page.
     */
    @Query("{'customer.id': ?0, deleted: false, visibility: 0}")
    List<AdPreviewProjectionDto> getProfileAds(String customerId);
}
