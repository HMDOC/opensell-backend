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
     * Query that will be executed when the user search ads in the catalog.
     *
     * @author Davide, Achraf
     */
    @Query("""
    {
        deleted: false,
        visibility: 0,
        $and: [
            { $or: [ { $expr: { $eq: [?4, null] } }, { 'adCategory.id': ?4 } ] },
            { $or: [ { $expr: { $eq: [?5, null] } }, { sold: ?5 } ] },
            { $or: [ { $expr: { $eq: [?3, null] } }, { shape: ?3 } ] },
            { $or: [ { $expr: { $eq: [?0, ''] } }, { title: { $regex: '?0', $options: 'i' } }, { description: { $regex: '?0', $options: 'i' } } ] },
            { $or: [ { $expr: { $eq: [?6, []] } }, { tags: { $all: ?6 } } ] }
        ],
        price: { $gte: ?1, $lte: ?2 },
    }
    """)
    List<AdPreviewProjectionDto> getAdSearch(
        String searchName, Double priceMin, Double priceMax,
        Integer shape, String typeId, Boolean filterSold,
        String[] tags, Sort sort
    );

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
