package com.opensell.customer.auth;

import org.springframework.data.mongodb.repository.ExistsQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;
import com.opensell.model.Customer;

@Repository
public interface AuthRepository extends MongoRepository<Customer, String> {
    @Query("{ isDeleted: false, isActivated: true, $or: [{ email: ?0 }, { username: ?0 }] }")
    Customer getUser(String usernameOrEmail);

    @ExistsQuery("{email: ?0}")
    boolean existsByEmail(String email);

    @ExistsQuery("{username: ?0}")
    boolean existsByUsername(String username);

    /**
     * Query that will set the field isActivated to true, and it will remove the code that the user used.
     */
    @Query("{email: ?0}")
    @Update("""
    {
        $set: { isActivated: true },
        $pull : {
            verificationCodes: { code: ?1 }
        }
    }
    """)
    int activateAccount(String email, String verificationCode);
}
