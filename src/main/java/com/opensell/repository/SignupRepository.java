package com.opensell.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.opensell.entities.Customer;

import jakarta.transaction.Transactional;

/**
 * @author Quoc Dung
 */
@Transactional
@Repository
public interface SignupRepository extends JpaRepository<Customer, Integer> {
    @Query(value = "SELECT COUNT(*) FROM customer c WHERE c.username = ?1", nativeQuery = true)
    public int checkUsername (String username);

    @Query(value = "SELECT COUNT(*) FROM customer c WHERE c.personal_email = ?1", nativeQuery = true)
    public int checkEmail (String email);

   // @Modifying
   // @Query(value = "INSERT INTO customer", nativeQuery = true)
    // il manque le query pour creer un nouveau utilisateur
 }
