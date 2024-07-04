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

    //to remove
    public abstract Customer findCustomerByLink(String link);

    //
    public abstract Customer findCustomerByIdCustomer(int id);

    //login
    @Query(value= "SELECT * FROM customer c WHERE c.personal_email = ?1 OR c.username = ?1 AND c.is_deleted = false AND c.is_activated = true", nativeQuery = true)
    public abstract Customer getUser(String usernameOrEmail);

    //signup
    public abstract int countByUsername(String username);

    //signup
    public abstract int countByPersonalEmail(String email);

    @Query(value = "SELECT count(c.pwd) from customer c where c.id_customer = ?1 and c.pwd = ?2", nativeQuery = true)
    public abstract int checkSamePwd(int customerId, String pwd);

    @Modifying
    @Query(value = "UPDATE customer c set c.is_activated = 1 where c.personal_email = ?1", nativeQuery = true)
    public int makeActivated(String email);


}
