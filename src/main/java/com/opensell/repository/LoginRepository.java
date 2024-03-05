package com.opensell.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.opensell.entities.Customer;


@Repository
@Transactional
public interface LoginRepository extends JpaRepository<Customer, Integer> {

    public abstract Customer findCustomerByLink(String link);

    //addSocialLink
    @Modifying
    @Query(value = "insert into customer_social_link(customer_info_id, link) values (?1, ?2)", nativeQuery = true)
    public void addSocialLink(int customerId, String link);

    @Query(value = "select count(*) from customer_social_link c where c.customer_info_id = ?1", nativeQuery = true)
    public int countSocialLinkById(int id);

    //login
    @Query(value= "SELECT COUNT(*) FROM customer c WHERE c.username = ?1 AND c.pwd = ?2 OR c.personal_email = ?1 AND c.pwd = ?2", nativeQuery = true)
    public abstract int checkLogin(String usernameOrEmail, String pwd);

    //signup
    public abstract int countByUsername(String username);

    //signup
    public abstract int countByPersonalEmail(String email);

    @Modifying
    @Query(value = "UPDATE customer c set c.is_verified = 1 where c.personal_email = ?1", nativeQuery = true)
    public int makeVerified(String email);


}
