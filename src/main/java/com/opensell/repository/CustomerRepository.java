package com.opensell.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.opensell.entities.Customer;
import com.opensell.entities.dto.CustomerDto;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    public Customer findOneByIdCustomerAndIsDeletedFalseAndIsActivatedTrue(Integer idCustomer);
    public boolean existsByLink(String link);
}