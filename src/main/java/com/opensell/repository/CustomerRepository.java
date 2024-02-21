package com.opensell.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.opensell.entities.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    public abstract Customer findCustomerByLink(String link);

    //login
    @Query(value = "SELECT COUNT(*) FROM customer c WHERE c.username = ?1 OR c.personal_email = ?1", nativeQuery = true)
    public int checkUsernameOrPersonalEmail(String usernameOrEmail);

    //login
    @Query(value= "SELECT COUNT(*) FROM customer c WHERE c.username = ?1 AND c.pwd = ?2 OR c.personal_email = ?1 AND c.pwd = ?2", nativeQuery = true)
    public abstract int checkPassword(String usernameOrEmail, String pwd);

    //signup
    public abstract int countByUsername(String username);

    //signup
    public abstract int countByPersonalEmail(String email);

    // @Modifying
    // @Query(value = "INSERT INTO customer", nativeQuery = true)
    // il manque le query pour creer un nouveau utilisateur



}
