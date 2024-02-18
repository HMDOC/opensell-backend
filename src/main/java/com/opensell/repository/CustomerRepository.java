package com.opensell.repository;

import com.opensell.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    public abstract Customer findCustomerByLink(String link);

}
