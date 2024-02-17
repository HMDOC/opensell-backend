package com.opensell.repository;

import com.opensell.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author Quoc Dung
 */

@Repository
public interface LoginRepository extends JpaRepository<Customer, Integer> {
    @Query(value = "SELECT COUNT(*) FROM customer c WHERE c.username = ?1 OR c.personal_email = ?1", nativeQuery = true)
    public int checkEmailUsername(String username);

    @Query(value= "SELECT COUNT(*) FROM customer c WHERE c.username = ?1 AND c.pwd = ?2 OR c.personal_email = ?1 AND c.pwd = ?2", nativeQuery = true)
    public int checkPassword(String username, String pwd);
}
