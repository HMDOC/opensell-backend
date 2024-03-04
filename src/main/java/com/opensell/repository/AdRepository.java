package com.opensell.repository;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.opensell.entities.Ad;
import com.opensell.repository.adaptive.common.AdaptiveRepository;
import com.opensell.repository.adaptive.common.TableInfo;
import jakarta.transaction.Transactional;

@Transactional
@Repository
public interface AdRepository extends JpaRepository<Ad, Integer>, AdaptiveRepository {
	public static final List<String> NO_JDBC_COLS = Arrays.asList("adType", "adTags", "adImages");
	public static final List<String> NOT_UPDATABLE = Arrays.asList("idAd", "addedDate", "link", "customer");
	
	public static final TableInfo TABLE_INFO = new TableInfo(
		"idAd",
		NO_JDBC_COLS,
		NOT_UPDATABLE,
		"ad",
		AdaptiveRepository.getClassField(Ad.class)
	);

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
            ( a.adType.idAdType = :typeId OR :typeId is null ) )\
            """)
	public List<Ad> getAdSearch(@Param("search") String searchName, @Param("pMin") Double priceMin,@Param("pMax") Double priceMax,
			@Param("dMin") Date dateMin, @Param("dMax") Date dateMax, @Param("shapeId") Integer shapeId, 
			@Param("typeId") Integer typeId, @Param("sort") Sort sort);


	/**
	 * Return an ad that have the idAd in parameter if it is not deleted.
	 *
	 * Purpose : To modify an ad
	 * @author Achraf
	 */
	@Query(value = "SELECT * FROM ad a WHERE a.id_ad = ?1 AND a.is_deleted = false LIMIT 1", nativeQuery = true)
	public Ad getAdByIdAd(int idAd);
	
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
}

/*
 "SELECT a.title, a.price, a.shape, a.isSold, a.addedDate, a.link, img.path FROM Ad a, AdImage img "
			+ "img.adId ON a.idAd = img.adId" // Put it in comment because I do not know what you are trying to do but maybe the native one is better.
			+ "WHERE (a.isDeleted = false AND a.visibility != 1 AND (a.title LIKE ?1 OR a.description LIKE ?1)) "
			+ "ORDER BY a.addedDate DESC"
 */