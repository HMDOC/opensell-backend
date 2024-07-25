package com.opensell.customer.setting.verification;

import com.opensell.repository.CustomerModificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerificationService {
    private final CustomerModificationRepository customerModificationRepository;

    public boolean isUsernameExists(int id, String username) {
        return customerModificationRepository.isUsernameExist(id, username) > 0;
    }

    public boolean isEmailExists(int id, String email) {
        return customerModificationRepository.isEmailExist(id, email) > 0;
    }
}
