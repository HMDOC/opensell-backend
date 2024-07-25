package com.opensell.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.opensell.model.verification.VerificationCode;

@Repository
public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Integer> {
    @Query(value = "select code from verification_code vc, customer c where c.email = ?1 and c.id = vc.customer_id", nativeQuery = true)
    String getCodeByEmail(String email);
}
