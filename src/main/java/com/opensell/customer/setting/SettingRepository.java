package com.opensell.customer.setting;

import org.springframework.data.mongodb.repository.ExistsQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;
import com.opensell.model.Customer;

/**
 * @author Olivier Mansuy
 * @Note Methods given by the repository are not used so that we can have all the modification methods
 * at the same place
 */
@Repository
public interface SettingRepository extends MongoRepository<Customer, String> {
    @Query("{id: ?1}")
    @Update("{$set: {iconPath: ?0}}")
    int updateCustomerIconPath(String iconPath, String customerId);

    //@Query(value = "SELECT EXISTS(SELECT * FROM customer c WHERE c.id != ?1 AND c.email = ?2)", nativeQuery = true)
    @ExistsQuery("{id: {$ne: ?0}, email: ?1}")
    boolean isEmailExist(String id, String email);

    //@Query(value = "SELECT EXISTS(SELECT * FROM customer c WHERE c.id != ?1 AND c.username = ?2)", nativeQuery = true)
    @ExistsQuery("{id: {$ne: ?0}, username: ?1}")
    boolean isUsernameExist(String id, String username);
}
