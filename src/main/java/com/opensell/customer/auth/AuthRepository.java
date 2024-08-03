package com.opensell.customer.auth;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.opensell.model.Customer;


@Repository
public interface AuthRepository extends MongoRepository<Customer, Integer> {
    //login
    @Query("SELECT * FROM customer c WHERE c.email = ?1 OR c.username = ?1 AND c.is_deleted = false AND c.is_activated = true")
    Customer getUser(String usernameOrEmail);

    @Query("SELECT count(c.email) FROM customer c WHERE c.email = ?1 LIMIT 1")
    int findOneByEmail(String email);

    @Query("SELECT count(c.email) FROM customer c WHERE c.username = ?1 LIMIT 1")
    int findOneByUsername(String email);

    @Query(value = "UPDATE customer c SET c.is_activated = 1 where c.email = ?1 LIMIT 1")
    int activateAccount(String email);
}
