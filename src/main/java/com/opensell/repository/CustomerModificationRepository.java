package com.opensell.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.opensell.entities.Customer;

import jakarta.transaction.Transactional;

/**
 * @author Olivier Mansuy
 */
@Repository
@Transactional
public interface CustomerModificationRepository extends CrudRepository<Customer, Integer> {

    @Modifying
    @Query(value = "update customer c set c.personal_email = ?1 where c.id_customer = ?2", nativeQuery = true)
    public abstract int updateCustomerPersonalEmail(String email, Integer id_customer);

    @Modifying
    @Query(value = "update customer c set c.username = ?1 where c.id_customer = ?2", nativeQuery = true)
    public abstract int updateCustomerUsername(String name, Integer id_customer);

    @Modifying
    @Query(value = "update customer c set c.pwd = ?1 where c.id_customer = ?2", nativeQuery = true)
    public abstract int updateCustomerPwd(String pwd, Integer id_customer);

    @Modifying
    @Query(value = "update customer_info c set c.first_name = ?1 where c.id_customer_info = ?2", nativeQuery = true)
    public abstract int updateCustomerFirstName(String firstName, Integer id_customer);

    @Modifying
    @Query(value = "update customer_info c set c.last_name = ?1 where c.id_customer_info = ?2", nativeQuery = true)
    public abstract int updateCustomerLastName(String lastName, Integer id_customer);

    @Modifying
    @Query(value = "update customer_info c set c.phone_number = ?1 where c.id_customer_info = ?2", nativeQuery = true)
    public abstract int updateCustomerPhoneNumber(String phoneNumber, Integer id_customer);

    @Modifying
    @Query(value = "update customer_info c set c.primary_address = ?1 where c.id_customer_info = ?2", nativeQuery = true)
    public abstract int updateCustomerPrimaryAddress(String address, Integer id_customer);

    @Modifying
    @Query(value = "update customer_info c set c.bio = ?1 where c.id_customer_info = ?2", nativeQuery = true)
    public abstract int updateCustomerBio(String bio, Integer id_customer);

    @Modifying
    @Query(value = "update customer_info c set c.icon_path = ?1 where c.id_customer_info = ?2", nativeQuery = true)
    public abstract int updateCustomerIconPath(String iconPath, Integer id_customer);

    @Modifying
    @Query(value = "update customer_info c set c.exposed_email = ?1 where c.id_customer_info = ?2", nativeQuery = true)
    public abstract int updateCustomerExposedEmail(String email, Integer id_customer);

    @Modifying
    @Query(value = "update customer_social_link c set c.link = ?1 where c.customer_info_id = ?2 and c.link = ?3 limit 1", nativeQuery = true)
    public abstract int updateCustomerSocialLink(String link, Integer id_customer, String oldLink);
}
