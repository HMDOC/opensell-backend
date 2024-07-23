package com.opensell.repository;

import com.opensell.ad.catalog.dto.AdPreviewDto;
import com.opensell.model.Ad;
import com.opensell.model.Customer;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface AdRepository extends JpaRepository<Ad, Integer> {
    /**
     * @author Davide
     */
    // https://www.baeldung.com/spring-jpa-like-queries
    // https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html
    @Query("""
        SELECT a FROM Ad a \
        WHERE ( a.isDeleted = false AND a.visibility = 0 AND \
        ( UPPER(a.title) LIKE %:search% OR UPPER(a.description) LIKE %:search% ) AND \
        ( a.price between :pMin And :pMax ) AND \
        ( a.addedDate between :dMin AND :dMax ) AND \
        ( a.shape = :shapeId OR :shapeId is null ) AND\
        ( a.adType.idAdType = :typeId OR :typeId is null ) AND \
        ( a.isSold = :filterSold OR :filterSold is null) )\
        """)
    List<Ad> getAdSearch(@Param("search") String searchName, @Param("pMin") Double priceMin, @Param("pMax") Double priceMax,
                                @Param("dMin") Date dateMin, @Param("dMax") Date dateMax, @Param("shapeId") Integer shapeId,
                                @Param("typeId") Integer typeId, @Param("filterSold") Boolean filterSold, @Param("sort") Sort sort);

    /**
     * Return an ad that have the idAd in parameter if it is not deleted.
     * <p>
     * Purpose : To modify an ad
     *
     * @author Achraf
     */
    @Query(value = "SELECT * FROM ad a WHERE a.id_ad = ?1 AND a.is_deleted = false LIMIT 1", nativeQuery = true)
    Ad getAdToModify(int idAd);

    Ad findOneByIdAdAndIsDeletedFalse(Integer idAd);

    Optional<Ad> findOneByTitleAndCustomerIdCustomerAndIsDeletedFalse(String title, long customerId);

    @Modifying
    @Query(value = "UPDATE ad a SET a.is_deleted = 1 WHERE a.id_ad = ?1 LIMIT 1", nativeQuery = true)
    int hideAd(Integer idAd);

    @Query("SELECT new com.opensell.ad.catalog.dto.AdPreviewDto(a) FROM Ad a WHERE a.customer = ?1 AND a.isDeleted = false")
    List<AdPreviewDto> getCustomerAds(Customer customer);
}
