package com.opensell.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.opensell.entities.Customer;

import jakarta.transaction.Transactional;

/**
 * @author Olivier Mansuy
 * @Note Methods given by the repository are not used so that we can have all the modification methods
 * at the same place
 */
@Repository
@Transactional
public interface CustomerModificationRepository extends CrudRepository<Customer, Integer> {

    @Modifying
    @Query(value = "update customer c set c.personal_email = ?1 where c.link = ?2", nativeQuery = true)
    public abstract int updateCustomerPersonalEmail(String email, String link);

    @Modifying
    @Query(value = "update customer c set c.username = ?1 where c.link = ?2", nativeQuery = true)
    public abstract int updateCustomerUsername(String name, String link);

    @Modifying
    @Query(value = "update customer c set c.pwd = ?1 where c.link = ?2", nativeQuery = true)
    public abstract int updateCustomerPwd(String pwd, String link);

    @Modifying
    @Query(value = "update customer_info ci, customer c set ci.first_name = ?1 where c.id_customer = ci.id_customer_info and c.link = ?2", nativeQuery = true)
    public abstract int updateCustomerFirstName(String firstName, String link);

    @Modifying
    @Query(value = "update customer_info ci, customer c set ci.last_name = ?1 where c.id_customer = ci.id_customer_info and c.link = ?2", nativeQuery = true)
    public abstract int updateCustomerLastName(String lastName, String link);

    @Modifying
    @Query(value = "update customer_info ci, customer c set ci.phone_number = ?1 where c.id_customer = ci.id_customer_info and c.link = ?2", nativeQuery = true)
    public abstract int updateCustomerPhoneNumber(String phoneNumber, String link);

    @Modifying
    @Query(value = "update customer_info ci, customer c set ci.primary_address = ?1 where c.id_customer = ci.id_customer_info and c.link = ?2", nativeQuery = true)
    public abstract int updateCustomerPrimaryAddress(String address, String link);

    @Modifying
    @Query(value = "update customer_info ci, customer c set ci.bio = ?1 where c.id_customer = ci.id_customer_info and c.link = ?2", nativeQuery = true)
    public abstract int updateCustomerBio(String bio, String link);

    @Modifying
    @Query(value = "update customer_info ci, customer c set ci.icon_path = ?1 where c.id_customer = ci.id_customer_info and c.link = ?2", nativeQuery = true)
    public abstract int updateCustomerIconPath(String iconPath, String link);

    @Modifying
    @Query(value = "update customer_info ci, customer c set ci.exposed_email = ?1 where c.id_customer = ci.id_customer_info and c.link = ?2", nativeQuery = true)
    public abstract int updateCustomerExposedEmail(String email, String link);

    @Modifying
    @Query(value = "UPDATE customer_social_link cs, customer c SET cs.link = ?1 WHERE cs.customer_info_id = c.id_customer AND c.link = ?2 AND cs.link = ?3 LIMIT 1", nativeQuery = true)
    public abstract int updateCustomerSocialLink(String link, String customer_link, String oldLink);
}
