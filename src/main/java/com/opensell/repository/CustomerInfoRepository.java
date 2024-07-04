package com.opensell.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.opensell.model.customer.CustomerInfo;


@Repository
public interface CustomerInfoRepository extends JpaRepository<CustomerInfo, Integer> {
    public int countByExposedEmail(String email);
    public int countByPhoneNumber(String phoneNumber);
    public abstract CustomerInfo findById(int id);
}
