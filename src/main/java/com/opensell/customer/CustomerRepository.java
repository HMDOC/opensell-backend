package com.opensell.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import com.opensell.model.Customer;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Customer findOneByIdAndIsDeletedFalseAndIsActivatedTrue(Integer id);
    Customer findOneByUsernameAndIsDeletedFalseAndIsActivatedTrue(String username);

    int deleteAllByIsActivatedFalseAndVerificationCodesIsEmpty();
}