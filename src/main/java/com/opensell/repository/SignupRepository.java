package com.opensell.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.opensell.entities.Customer;

/**
 * @author Quoc Dung
 */

 @Repository
 public interface SignupRepository extends JpaRepository<Customer, Integer> {
    @Query(value = "SELECT COUNT(*) FROM customer c WHERE c.username = ?1", nativeQuery = true)
    public int checkUsername (String username);

    @Query(value = "SELECT COUNT(*) FROM customer c WHERE c.personal_email = ?1", nativeQuery = true)
    public int checkEmail (String email);

    // il manque le query pour creer un nouveau utilisateur
 }
