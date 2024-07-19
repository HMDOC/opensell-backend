package com.opensell.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.opensell.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Customer findOneByIdCustomerAndIsDeletedFalseAndIsActivatedTrue(Integer idCustomer);
    Customer findOneByUsernameAndIsDeletedFalseAndIsActivatedTrue(String username);
}