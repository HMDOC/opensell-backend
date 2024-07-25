package com.opensell.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.opensell.model.Customer;


@Repository
@Transactional
public interface LoginRepository extends JpaRepository<Customer, Integer> {
    //login
    @Query(value= "SELECT * FROM customer c WHERE c.email = ?1 OR c.username = ?1 AND c.is_deleted = false AND c.is_activated = true", nativeQuery = true)
    Customer getUser(String usernameOrEmail);

    //signup
    int countByUsername(String username);

    @Query(value = "SELECT count(c.email) FROM customer c WHERE c.email = ?1 AND c.is_activated = 1", nativeQuery = true)
    int findCustomerByPersonalEmail(String email);

    @Query(value = "SELECT count(c.email) FROM customer c WHERE c.email = ?1 AND c.is_activated = 0", nativeQuery = true)
    int findCustomerByPersonalEmailNotActivated(String email);

    @Modifying
    @Query(value = "UPDATE customer c set c.is_activated = 1 where c.email = ?1", nativeQuery = true)
    int makeActivated(String email);
}
