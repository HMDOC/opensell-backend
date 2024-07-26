package com.opensell.customer.auth;

import com.opensell.enums.VerificationCodeType;
import com.opensell.model.Customer;
import com.opensell.model.customer.VerificationCode;
import com.opensell.repository.CustomerRepository;
import com.opensell.repository.LoginRepository;
import com.opensell.repository.VerificationCodeRepository;
import com.opensell.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthService {
    private final CustomerRepository customerRepository;
    private final VerificationCodeRepository verificationCodeRepository;
    private final LoginRepository loginRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    /**
     * Function to generate a random code. This function is used for the code attribute in the entity <b>Verification Code</b>
     *
     * @since 1.0
     */
    public String generateCode() {
        return String.valueOf((int) (Math.random() * 999999 - 111111) + 111111);
    }

    /**
     * Job to delete all account that are not activated and that have zero validation code.
     *
     * @since 1.0
     */
    @Scheduled(fixedRate = 600000, initialDelay = 600000)
    protected void emailCleanUp() {
        System.out.println(customerRepository.deleteAllByIsActivatedFalseAndVerificationCodesIsEmpty() + " unactivated account deleted.");
    }

    /**
     * Job to delete all unused <b>FIRST_SIGN_UP</b> code than have been created 10 minutes ago or longer.
     *
     * @since 1.0
     */
    @Scheduled(fixedRate = 60000, initialDelay = 60000)
    public void codeCleanUp() {
        System.out.println(verificationCodeRepository.deleteExpiredCode(10, VerificationCodeType.FIRST_SIGN_UP) + " expired codes deleted.");
    }

    /**
     * Function who create a new Customer and email him with a verification code.
     *
     * @since 1.0
     */
    public int signup(String email, String username, String pwd) {
        if (loginRepository.findOneByEmail(email) == 1) {
            return 1; // Email already exists
        } else if (loginRepository.findOneByUsername(username) > 0) {
            return 2; // Username already exists
        }

        VerificationCode verificationCode = VerificationCode.builder()
            .code(this.generateCode())
            .type(VerificationCodeType.FIRST_SIGN_UP)
            .build();

        Customer customer = Customer.builder()
            .username(username)
            .email(email)
            .pwd(passwordEncoder.encode(pwd))
            .verificationCodes(List.of(verificationCode))
            .build();

        loginRepository.save(customer);

        if (emailService.sendEmail(email, "Welcome to OpenSell",
            "Thank you for signing up with OpenSell! Here's your verification code:\n" + verificationCode.getCode())) {
            return 3; // Email sent
        }

        return 4; // Email not send
    }

    /**
     * Function to authenticate the user.
     *
     * @since 1.0
     */
    public Integer login(String username, String pwd) {
        Customer customer = loginRepository.getUser(username);

        if (customer != null && passwordEncoder.matches(pwd, customer.getPwd())) {
            return customer.getId();
        }

        return null;
    }

    /**
     * Get the essential data of the user that the frontend need.
     *
     * @since 1.0
     */
    public CustomerDto getCustomerDto(int id) {
        Customer customer = customerRepository.findOneByIdAndIsDeletedFalseAndIsActivatedTrue(id);
        return customer != null ? new CustomerDto(customer) : null;
    }

    /**
     * Verify the code of the user.
     *
     * @since 1.0
     */
    public int verifyCode(String email, String inputCode) {
        if (verificationCodeRepository.getCodeByEmail(email).equals(inputCode)) {
            if (loginRepository.activateAccount(email) > 0) {
                return 0; // Email verified
            }
        }

        return 1; // Wrong code
    }
}
