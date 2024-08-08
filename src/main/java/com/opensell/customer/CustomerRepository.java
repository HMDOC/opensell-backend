package com.opensell.customer;

import com.opensell.enums.VerificationCodeType;
import com.opensell.model.Customer;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CustomerRepository extends MongoRepository<Customer, String> {
    Customer findOneByIdAndIsDeletedFalseAndIsActivatedTrue(String id);
    Customer findOneByUsernameAndIsDeletedFalseAndIsActivatedTrue(String username);

    @Aggregation({
        """
        {
            $match: {
                email: ?1,
            }
        }
        """,
        """
        {
            $project: {
                _id: false,
                email: true,
                verificationCodes: true
            }
        }
        """,
        """
        {
            $unwind: "$verificationCodes"
        }
        """,
        """
        {
            $match: {
                "verificationCodes.code": ?0,
                $expr: {
                    $lte: [{ $dateDiff: { startDate: "$verificationCodes.createdAt", endDate: new Date(), unit: "minute" } }, 10]
                }
            }
        }
        """,
        """
        {
            $count : "total"
        }
        """
    })
    Integer countByCodeAndCustomerEmailLimitOne(String code, String email);

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