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
    @Query(value = "update customer c set c.personal_email = ?1 where c.id_customer = ?2", nativeQuery = true)
    public abstract int updateCustomerPersonalEmail(String email, int customerId);

    @Modifying
    @Query(value = "update customer c set c.username = ?1 where c.id_customer = ?2", nativeQuery = true)
    public abstract int updateCustomerUsername(String name, int customerId);

    @Modifying
    @Query(value = "update customer c set c.pwd = ?1 where c.id_customer = ?2", nativeQuery = true)
    public abstract int updateCustomerPwd(String pwd, int customerId);

    @Modifying
    @Query(value = "update customer_info ci, customer c set ci.first_name = ?1 where c.customer_info_id = ci.id_customer_info and c.id_customer = ?2", nativeQuery = true)
    public abstract int updateCustomerFirstName(String firstName, int customerId);

    @Modifying
    @Query(value = "update customer_info ci, customer c set ci.last_name = ?1 where c.customer_info_id = ci.id_customer_info and c.id_customer = ?2", nativeQuery = true)
    public abstract int updateCustomerLastName(String lastName, int customerId);

    @Modifying
    @Query(value = "update customer_info ci, customer c set ci.phone_number = ?1 where c.customer_info_id = ci.id_customer_info and c.id_customer = ?2", nativeQuery = true)
    public abstract int updateCustomerPhoneNumber(String phoneNumber, int customerId);

    @Modifying
    @Query(value = "update customer_info ci, customer c set ci.bio = ?1 where ci.id_customer_info = c.customer_info_id and c.id_customer = ?2", nativeQuery = true)
    public abstract int updateCustomerBio(String bio, int customerId);

    @Modifying
    @Query(value = "update customer_info ci, customer c set ci.icon_path = ?1 where ci.id_customer_info = c.customer_info_id and c.id_customer = ?2", nativeQuery = true)
    public abstract int updateCustomerIconPath(String iconPath, int customerId);

    @Modifying
    @Query(value = "update customer_info ci, customer c set ci.exposed_email = ?1 where ci.id_customer_info = c.customer_info_id and c.id_customer = ?2", nativeQuery = true)
    public abstract int updateCustomerExposedEmail(String email, int customerId);
}
