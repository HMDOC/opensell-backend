package com.opensell.customer.setting.verification;

import com.opensell.customer.setting.SettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerificationService {
    private final SettingRepository settingRepository;

    public boolean isUsernameExists(String id, String username) {
        return settingRepository.isUsernameExist(id, username);
    }

    public boolean isEmailExists(String id, String email) {
        return settingRepository.isEmailExist(id, email);
    }
}
