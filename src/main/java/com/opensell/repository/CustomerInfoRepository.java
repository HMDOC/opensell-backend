package com.opensell.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.opensell.entities.customer.CustomerInfo;
import com.opensell.entities.dto.CustomerProfil;


@Repository
public interface CustomerInfoRepository extends JpaRepository<CustomerInfo, Integer> {
    public int countByExposedEmail(String email);
    public int countByPhoneNumber(String phoneNumber);
    public abstract CustomerInfo findById(int id);
}
