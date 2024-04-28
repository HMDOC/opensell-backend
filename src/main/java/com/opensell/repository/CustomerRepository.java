package com.opensell.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.opensell.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    public Customer findOneByIdCustomerAndIsDeletedFalseAndIsActivatedTrue(Integer idCustomer);
    public Customer findOneByLinkAndIsDeletedFalseAndIsActivatedTrue(String link);
    public boolean existsByLink(String link);
}