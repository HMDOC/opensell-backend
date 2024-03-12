package com.opensell.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.opensell.entities.customer.CustomerInfo;

@Repository
public interface CustomerInfoRepository extends JpaRepository<CustomerInfo, Integer> {
    
    @Query(value = "SELECT ci.* FROM customer_info ci, customer c WHERE c.customer_info_id = ci.id_customer_info AND c.link = ?1 AND c.is_deleted = 0 AND c.is_activated = 1", nativeQuery = true)
    public CustomerInfo findCustomerInfoByLink(String link);

    //addSocialLink
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO customer_social_link(customer_info_id, link) VALUES ((SELECT c.id_customer FROM customer c WHERE c.link = ?1), ?2)", nativeQuery = true)
    public int addSocialLink(String customer_link, String link);

    @Query(value = "SELECT COUNT(cs.link) FROM customer c, customer_social_link cs  WHERE c.id_customer = cs.customer_info_id AND c.link = ?1", nativeQuery = true)
    public int countSocialLinkByCustomerLink(String link);

    public int countByExposedEmail(String email);
    public int countByPhoneNumber(String phoneNumber);
}
