package com.opensell.customer.auth;

import com.opensell.model.verification.VerificationCode;
import com.opensell.repository.CustomerRepository;
import com.opensell.repository.VerificationCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthService {
    private final CustomerRepository customerRepository;
    private final VerificationCodeRepository verificationCodeRepository;

    /**
     * Function to generate a random code. This function is used for the code attribute in the entity <b>Verification Code</b>
     * @since 1.0
     */
    public String generateCode() {
        return String.valueOf((int) (Math.random() * 999999 - 111111) + 111111);
    }

    /**
     * Job to delete all account that are not activated and that have zero validation code.
     * @since 1.0
     */
    @Scheduled(fixedRate = 600000, initialDelay = 600000)
    protected void emailCleanUp() {
        System.out.println(customerRepository.deleteAllByIsActivatedFalseAndVerificationCodesIsEmpty() + " unactivated account deleted.");
    }

    /**
     * Job to delete all unused <b>FIRST_SIGN_UP</b> code than have been created 10 minutes ago or longer.
     * @since 1.0
     */
    @Scheduled(fixedRate = 60000, initialDelay = 60000)
    public void codeCleanUp() {
        System.out.println(verificationCodeRepository.deleteExpiredCode(10, VerificationCode.VerificationCodeType.FIRST_SIGN_UP) + " expired codes deleted.");;
    }
}
