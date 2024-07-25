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
import com.opensell.service.EmailService;
import java.sql.Date;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final LoginRepository loginRepository;
    private final EmailService emailService;
    private final CodeService codeService;
    private final VerificationCodeRepository codeRepository;
    private final CustomerInfoRepository customerInfoRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepo;

    @GetMapping("/login")
    public Integer login(@RequestParam String username, @RequestParam String pwd) {
        Customer customer = loginRepository.getUser(username);

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
        if (loginRepository.findOneByEmail(email) == 1) {
            return 1; // Email already exists
        } else if (loginRepository.findOneByUsername(username) > 0) {
            return 2; // Username already exists
        } else {
            String code = codeService.generateCode();
            Date now = new Date(System.currentTimeMillis());

            VerificationCode newCode = new VerificationCode();
            Customer customer = new Customer();
            CustomerInfo infos = new CustomerInfo();

            customerInfoRepository.save(infos);

            customer.setUsername(username);
            customer.setEmail(email);
            customer.setPwd(passwordEncoder.encode(pwd));
            customer.setIsDeleted(false);
            customer.setIsVerified(false);
            customer.setIsActivated(false);
            customer.setCustomerInfo(infos);
            customer.setJoinedDate(now);

            newCode.setCustomer(customer);
            newCode.setCode(code);
            newCode.setType(VerificationCodeType.FIRST_SIGN_UP);
            newCode.setCreatedAt(LocalDateTime.now());

            loginRepository.save(customer);
            codeRepository.save(newCode);
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
        if (codeRepository.getCodeByEmail(email).equals(inputCode)) {
            if (loginRepository.activateAccount(email) > 0) {
                return 0; // Email verified
            }
        }
        return 1; // Wrong code
    }
}
