package com.opensell.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.opensell.entities.customer.CustomerInfo;


@Repository
public interface CustomerInfoRepository extends JpaRepository<CustomerInfo, Integer> {
    
    @Query(value = "SELECT ci.* FROM customer_info ci, customer c WHERE c.customer_info_id = ci.id_customer_info AND c.link = ?1 AND c.is_deleted = 0 AND c.is_activated = 1", nativeQuery = true)
    public CustomerInfo findCustomerInfoByLink(String link);
    public int countByExposedEmail(String email);
    public int countByPhoneNumber(String phoneNumber);
    public abstract CustomerInfo findById(int id);
}
