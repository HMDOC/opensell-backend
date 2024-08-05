package com.opensell.customer;

import com.opensell.enums.VerificationCodeType;
import com.opensell.model.Customer;
import org.springframework.data.mongodb.repository.CountQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CustomerRepository extends MongoRepository<Customer, String> {
    Customer findOneByIdAndIsDeletedFalseAndIsActivatedTrue(String id);
    Customer findOneByUsernameAndIsDeletedFalseAndIsActivatedTrue(String username);

    // REDO : Need to fix this query to work with the expiration date
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
                type: ?1
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
    int deleteExpiredCode(int numberOfMinutes, VerificationCodeType verificationCodeType);
}