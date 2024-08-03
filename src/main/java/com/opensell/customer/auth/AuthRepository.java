package com.opensell.customer.auth;

import org.springframework.data.mongodb.repository.ExistsQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

import com.opensell.model.Customer;


@Repository
public interface AuthRepository extends MongoRepository<Customer, String> {
    //login
    //@Query("SELECT * FROM customer c WHERE c.email = ?1 OR c.username = ?1 AND c.is_deleted = false AND c.is_activated = true")
    @Query("{ isDeleted: false, isActivated: true, $or: [{ email: ?0 }, { username: ?0 }] }")
    Customer getUser(String usernameOrEmail);

    //@Query("SELECT count(c.email) FROM customer c WHERE c.email = ?1 LIMIT 1")
    @ExistsQuery("{email: ?0}")
    boolean existsByEmail(String email);

    //@Query("SELECT count(c.username) FROM customer c WHERE c.username = ?1 LIMIT 1")
    @ExistsQuery("{username: ?0}")
    boolean existsByUsername(String username);

    //@Query(value = "UPDATE customer c SET c.is_activated = 1 where c.email = ?1 LIMIT 1")
    @Query("{email: ?0}")
    @Update("{$set: {isActivated: true}}")
    int activateAccount(String email);
}
