package com.opensell.customer;

import com.opensell.enums.VerificationCodeType;
import com.opensell.model.Customer;
import org.springframework.data.mongodb.repository.CountQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CustomerRepository extends MongoRepository<Customer, String> {
    Customer findOneByIdAndIsDeletedFalseAndIsActivatedTrue(String id);
    Customer findOneByUsernameAndIsDeletedFalseAndIsActivatedTrue(String username);

    // Need to do the query to delete I code when it had been use to activate an account.
    int deleteAllByIsActivatedFalseAndVerificationCodesIsEmpty();

    //@Query(value = "SELECT COUNT(vc.code) FROM verification_code vc INNER JOIN customer c ON vc.customer_id = c.id WHERE c.email = ?2 AND vc.code = ?1 LIMIT 1")
    @CountQuery("""
    {
        email: ?1,
        verificationCodes: {
            $elemMatch : {
                code: ?0,
                type: 'FIRST_SIGN_UP'
            }
        }
    }
    """)
    int countByCodeAndCustomerEmailLimitOne(String code, String email);

    // REDO, not working
    //@Query("DELETE vc FROM verification_code vc WHERE vc.type = :#{#type.name()} AND TIMESTAMPDIFF(MINUTE, vc.created_at, CURRENT_TIMESTAMP) > ?1")
    @Query(value = """
    {
        verificationCodes: {
            $elemMatch : {
                code: ?0,
                type: 'FIRST_SIGN_UP'
            }
        }
    }
    """, delete = true
    )
    /*
    *$expr: {
            $gte: [{ $dateDiff: { startDate: "$verificationCodes.createdAt", endDate: new Date(), unit: "minutes" } }, 20]
        }
    */
    int deleteExpiredCode(int numberOfMinutes, @Param("type") VerificationCodeType verificationCodeType);
}