package com.opensell.repository;

import java.sql.Date;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.opensell.entities.Ad;
import com.opensell.entities.Customer;
import com.opensell.entities.dto.DisplayAdView;
import jakarta.transaction.Transactional;

@Transactional
@Repository
public interface AdRepository extends JpaRepository<Ad, Integer> {
	/**
	 * Return a ad by the link if it is not deleted and not private.
	 * 
	 * Purpose : For AdBuyerView
	 * @author Achraf
	 */
	@Query(value = "SELECT * FROM ad a WHERE a.link = ?1 AND a.is_deleted = false AND a.visibility != 1 LIMIT 1", nativeQuery = true)
	public Ad getAdByLink(String link);
	
	/**
	 * 
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
	public List<Ad> getAdSearch(@Param("search") String searchName, @Param("pMin") Double priceMin,@Param("pMax") Double priceMax,
			@Param("dMin") Date dateMin, @Param("dMax") Date dateMax, @Param("shapeId") Integer shapeId,
			@Param("typeId") Integer typeId, @Param("filterSold") Boolean filterSold, @Param("sort") Sort sort);


	/**
	 * Return an ad that have the idAd in parameter if it is not deleted.
	 *
	 * Purpose : To modify an ad
	 * @author Achraf
	 */
	@Query(value = "SELECT * FROM ad a WHERE a.link = ?1 AND a.is_deleted = false LIMIT 1", nativeQuery = true)
	public Ad getAdToModif(String link);
	
	public Ad findOneByIdAdAndIsDeletedFalse(Integer idAd);
	
	/***
	 * To see if a this customer already have an ad with this title.
	 * 
	 * Purpose : To modify an ad
	 * @author Achraf
	 */
	@Query(value = "SELECT EXISTS(SELECT * FROM ad a WHERE a.customer_id = ?1 AND a.title = ?2 LIMIT 1)", nativeQuery = true)
	public byte checkTitle(int idCustomer, String title);
	
	/***
	 * To see if a this customer already have an ad with this reference.
	 * 
	 * Purpose : To modify an ad
	 * @author Achraf
	 */
	@Query(value = "SELECT EXISTS(SELECT * FROM ad a WHERE a.customer_id = ?1 AND a.reference = ?2 LIMIT 1)", nativeQuery = true)
	public byte checkReference(int idCustomer, String reference);

	@Query(value = "SELECT ad.* FROM ad, customer c WHERE ad.customer_id = c.id_customer AND c.link = ?1", nativeQuery = true)
	public List<Ad> getAdsFromUser(String link);

	@Modifying
	@Query(value = "UPDATE ad a SET a.title = ?1 WHERE a.id_ad = ?2 LIMIT 1", nativeQuery = true)
	public int updateTitle(String title, int idAd);

	@Modifying
	@Query(value = "UPDATE ad a SET a.reference = ?1 WHERE a.id_ad = ?2 LIMIT 1", nativeQuery = true)
	public int updateReference(String reference, int idAd);

	@Modifying
	@Query(value = "UPDATE ad a SET a.price = ?1 WHERE a.id_ad = ?2 LIMIT 1", nativeQuery = true)
	public int updatePrice(double price, int idAd);

	@Modifying
	@Query(value = "UPDATE ad a SET a.address = ?1 WHERE a.id_ad = ?2 LIMIT 1", nativeQuery = true)
    public int updateAddress(String address, int idAd);

	@Modifying
	@Query(value = "UPDATE ad a SET a.is_sold = ?1 WHERE a.id_ad = ?2 LIMIT 1", nativeQuery = true)
    public int updateIsSold(boolean isSold, int idAd);
    
	@Modifying
	@Query(value = "UPDATE ad a SET a.description = ?1 WHERE a.id_ad = ?2 LIMIT 1", nativeQuery = true)
	public int updateDescription(String description, int idAd);
    
	@Modifying
	@Query(value = "UPDATE ad a SET a.visibility = ?1 WHERE a.id_ad = ?2 LIMIT 1", nativeQuery = true)
	public int updateVisibility(int visibility, int idAd);
    
	@Modifying
	@Query(value = "UPDATE ad a SET a.shape = ?1 WHERE a.id_ad = ?2 LIMIT 1", nativeQuery = true)
	public int updateShape(int shape, int idAd);


	@Query("SELECT new com.opensell.entities.dto.DisplayAdView(a) FROM Ad a WHERE a.customer = ?1")
	public List<DisplayAdView> getCustomerAds(Customer customer);

	@Modifying
	@Query(value = "insert into ad(ad_type_id, customer_id, price, shape, visibility, title, description, address, link, reference) value(?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9, ?10)", nativeQuery = true)
	public int createAd(int adTypeId, int customerId, double price, int shape, int visibility, String title, String description, String address, String link, String reference);

	@Modifying
	@Query(value = "insert into ad_image(ad_id, spot, path) value (?1, ?2, ?3)", nativeQuery = true)
	public int saveAdImage(int adId, int spot, String path);

	@Modifying
	@Query(value = "insert into ad_ad_tag_rel(ad_id, ad_tag_id) VALUES (?1, ?2)", nativeQuery = true)
	public int saveRelAdTag(int adId, int adTagId);

	public int countByLink(String link);

}

/*
 "SELECT a.title, a.price, a.shape, a.isSold, a.addedDate, a.link, img.path FROM Ad a, AdImage img "
			+ "img.adId ON a.idAd = img.adId" // Put it in comment because I do not know what you are trying to do but maybe the native one is better.
			+ "WHERE (a.isDeleted = false AND a.visibility != 1 AND (a.title LIKE ?1 OR a.description LIKE ?1)) "
			+ "ORDER BY a.addedDate DESC"
 */