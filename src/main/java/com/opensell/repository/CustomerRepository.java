package com.opensell.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.opensell.entities.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    public abstract Customer findCustomerByLink(String link);

}
