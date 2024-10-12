package com.opensell.controller;

import com.opensell.entities.Customer;
import com.opensell.entities.customer.CustomerInfo;
import com.opensell.entities.dto.CustomerDto;
import com.opensell.entities.verification.VerificationCode;
import com.opensell.entities.verification.VerificationCode.VerificationCodeType;
import com.opensell.repository.CustomerInfoRepository;
import com.opensell.repository.CustomerRepository;
import com.opensell.repository.LoginRepository;
import com.opensell.repository.VerificationCodeRepository;
import com.opensell.service.CodeService;
import com.opensell.service.timeService.*;
import jakarta.annotation.PostConstruct;
import com.opensell.service.EmailService;
import com.opensell.service.FileUploadService;
import com.opensell.service.FileUploadService.RandName;

import java.sql.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthenticationController {

    @Autowired
    private LoginRepository rep;

    @Autowired
    private EmailService emailService;

    @Autowired
    private CodeService codeService;

    @Autowired
    private VerificationCodeRepository codeRep;

    @Autowired
    private CustomerInfoRepository infosrep;

    @Autowired
    private CleanupCode cleanupCode;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomerRepository customerRepo;

    @GetMapping("/login")
    public Integer login(@RequestParam String username, @RequestParam String pwd) {
        Customer customer = rep.getUser(username);
        if (customer == null) {
            return null; // Utilisateur non trouvé
        }
        else {
            String encodedPass = customer.getPwd();
            if (passwordEncoder.matches(pwd, encodedPass)) {
                return customer.getIdCustomer();
            }
            return null; // Mauvais mot de passe
        }
    }

    @GetMapping("/getDto")
    public CustomerDto getCustomerDto(@RequestParam int idCustomer) {
        Customer customer = customerRepo.findOneByIdCustomerAndIsDeletedFalseAndIsActivatedTrue(idCustomer);
        if(customer != null) {
            return new CustomerDto(customer);
        } else return null;
    }

    @PostMapping("/signup")
    public int signup(@RequestParam String email, @RequestParam String username, @RequestParam String pwd) {
        if (rep.countByPersonalEmail(email) == 1) {
            return 1; // Email already exists
        } else if (rep.countByUsername(username) == 1) {
            return 2; // Username already exists
        } else {
            String code = codeService.generateCode();
            Date now = new Date(System.currentTimeMillis());
            String link = FileUploadService.randFileName("", RandName.URL);

            VerificationCode newCode = new VerificationCode();
            Customer customer = new Customer();
            CustomerInfo infos = new CustomerInfo();

            infosrep.save(infos);

            customer.setUsername(username);
            customer.setPersonalEmail(email);
            customer.setPwd(passwordEncoder.encode(pwd));
            customer.setIsDeleted(false);
            customer.setIsVerified(false);
            customer.setIsActivated(false);
            customer.setLink(link);
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
}
