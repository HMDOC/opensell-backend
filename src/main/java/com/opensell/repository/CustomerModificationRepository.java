package com.opensell.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.opensell.model.Customer;
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
    @Query(value = "UPDATE customer c SET c.username = ?1 WHERE c.id = ?2 LIMIT 1", nativeQuery = true)
    int updateCustomerUsername(String name, int customerId);

    @Modifying
    @Query(value = "UPDATE customer c SET c.pwd = ?1 WHERE c.id = ?2 LIMIT 1", nativeQuery = true)
    int updateCustomerPwd(String pwd, int customerId);

    @Modifying
    @Query(value = "UPDATE customer_info ci, customer c SET ci.first_name = ?1 WHERE c.customer_info_id = ci.id AND c.id = ?2 LIMIT 1", nativeQuery = true)
    int updateCustomerFirstName(String firstName, int customerId);

    @Modifying
    @Query(value = "UPDATE customer_info ci, customer c SET ci.last_name = ?1 WHERE c.customer_info_id = ci.id AND c.id = ?2 LIMIT 1", nativeQuery = true)
    int updateCustomerLastName(String lastName, int customerId);

    @Modifying
    @Query(value = "UPDATE customer_info ci, customer c SET ci.bio = ?1 WHERE ci.id = c.customer_info_id AND c.id = ?2 LIMIT 1", nativeQuery = true)
    int updateCustomerBio(String bio, int customerId);

    @Modifying
    @Query(value = "UPDATE customer_info ci, customer c SET ci.icon_path = ?1 WHERE ci.id = c.customer_info_id AND c.id = ?2 LIMIT 1", nativeQuery = true)
    int updateCustomerIconPath(String iconPath, int customerId);

    @Query(value = "SELECT EXISTS(SELECT * FROM customer c WHERE c.id != ?1 AND c.email = ?2)", nativeQuery = true)
    int isEmailExist(int id, String email);
}
