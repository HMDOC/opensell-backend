package com.opensell.repository;

import com.opensell.enums.VerificationCodeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.opensell.model.customer.VerificationCode;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Integer> {
    @Query(value = "SELECT COUNT(vc.code) FROM verification_code vc INNER JOIN customer c ON vc.customer_id = c.id WHERE c.email = ?2 AND vc.code = ?1 LIMIT 1", nativeQuery = true)
    int countByCodeAndCustomerEmailLimitOne(String code, String email);

    @Modifying
    @Query(value = "DELETE vc FROM verification_code vc WHERE vc.type = :#{#type.name()} AND TIMESTAMPDIFF(MINUTE, vc.created_at, CURRENT_TIMESTAMP) > ?1", nativeQuery = true)
    int deleteExpiredCode(int numberOfMinutes, @Param("type") VerificationCodeType verificationCodeType);
}
