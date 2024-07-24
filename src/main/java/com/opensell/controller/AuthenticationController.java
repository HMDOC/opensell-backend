package com.opensell.controller;

import com.opensell.model.Customer;
import com.opensell.model.customer.CustomerInfo;
import com.opensell.model.dto.CustomerDto;
import com.opensell.model.verification.VerificationCode;
import com.opensell.model.verification.VerificationCode.VerificationCodeType;
import com.opensell.repository.CustomerInfoRepository;
import com.opensell.repository.CustomerRepository;
import com.opensell.repository.LoginRepository;
import com.opensell.repository.VerificationCodeRepository;
import com.opensell.service.CodeService;
import com.opensell.service.time.*;
import jakarta.annotation.PostConstruct;
import com.opensell.service.EmailService;
import java.sql.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final LoginRepository rep;
    private final EmailService emailService;
    private final CodeService codeService;
    private final VerificationCodeRepository codeRep;
    private final CustomerInfoRepository customerInfoRepository;
    private final CleanupCode cleanupCode;
    private final EmailCleanup emailCleanup;
    private final PasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepo;

    @GetMapping("/login")
    public Integer login(@RequestParam String username, @RequestParam String pwd) {
        Customer customer = rep.getUser(username);

        if (customer != null && passwordEncoder.matches(pwd, customer.getPwd())) {
            return customer.getId();
        }

        return null;
    }

    @GetMapping("/getDto")
    public CustomerDto getCustomerDto(@RequestParam int idCustomer) {
        Customer customer = customerRepo.findOneByIdAndIsDeletedFalseAndIsActivatedTrue(idCustomer);
        if (customer != null) {
            return new CustomerDto(customer);
        } else
            return null;
    }

    @PostMapping("/signup")
    public int signup(@RequestParam String email, @RequestParam String username, @RequestParam String pwd) {
        if (rep.findCustomerByPersonalEmail(email) == 1) {
            return 1; // Email already exists
        } else if (rep.findCustomerByPersonalEmailNotActivated(email) == 1) {
            return 5; // Email already exists but not activated
        } else if (rep.countByUsername(username) == 1) {
            return 2; // Username already exists
        } else {
            String code = codeService.generateCode();
            Date now = new Date(System.currentTimeMillis());

            VerificationCode newCode = new VerificationCode();
            Customer customer = new Customer();
            CustomerInfo infos = new CustomerInfo();

            customerInfoRepository.save(infos);

            customer.setUsername(username);
            customer.setPersonalEmail(email);
            customer.setPwd(passwordEncoder.encode(pwd));
            customer.setIsDeleted(false);
            customer.setIsVerified(false);
            customer.setIsActivated(false);
            customer.setCustomerInfo(infos);
            customer.setJoinedDate(now);

            newCode.setCustomer(customer);
            newCode.setCode(code);
            newCode.setType(VerificationCodeType.FIRST_SIGN_UP);
            newCode.setCreatedAt(now);

            rep.save(customer);
            codeRep.save(newCode);
            if (emailService.sendEmail(email, "Welcome to OpenSell",
                    "Thank you for signing up with OpenSell! Here's your verification code:\n" + code)) {
                return 3; // Email sent
            }
            ;
            return 4; // Email not send
        }
    }

    @GetMapping("/verify-code")
    public int verifyCode(@RequestParam String email, @RequestParam String inputCode) {
        if (codeRep.getCodeByEmail(email).equals(inputCode)) {
            if (rep.makeActivated(email) == 1) {
                return 0; // Email verified
            }
        }
        return 1; // Wrong code
    }

    @PostConstruct
    public void runCodeCleanup() throws Exception {
        Job job = new Job(cleanupCode, 60000);
        job.start();
    }

    @PostConstruct
    public void runEmailCleanup() throws Exception {
        Job job = new Job(emailCleanup, 600000);
        job.start();
    }
}
