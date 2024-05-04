package com.opensell.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.opensell.entities.verification.VerificationCode;

@Repository
public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Integer> {

    @Query(value = "select code from verification_code vc, customer c where c.personal_email=?1 and c.id_customer = vc.customer_id", nativeQuery = true)
    public String getCodeByEmail(String email);
}
