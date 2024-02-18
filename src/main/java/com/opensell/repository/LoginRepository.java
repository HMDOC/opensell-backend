package com.opensell.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.opensell.entities.Customer;

/**
 * @author Quoc Dung
 */

@Repository
public interface LoginRepository extends JpaRepository<Customer, Integer> {
    @Query(value= "SELECT COUNT(*) FROM customer c WHERE c.username = ?1 AND c.pwd = ?2 UNION SELECT COUNT(*) FROM customer c where c.personal_Email = ?1 AND c.pwd = ?2", nativeQuery = true)
    public int findUserLogin(String username, String pwd);

    @Query(value = "SELECT COUNT(*) FROM customer c WHERE c.username = ?1", nativeQuery = true)
    public int findByUsername(String username);

    @Query(value= "SELECT COUNT(*) FROM customer c WHERE c.username = ?1 AND c.pwd = ?2", nativeQuery = true)
    public int checkPassword(String username, String pwd);
}
