package com.opensell.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.opensell.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    public Customer findOneByIdCustomerAndIsDeletedFalse(Integer idCustomer);
    public boolean existsByLink(String link);
}