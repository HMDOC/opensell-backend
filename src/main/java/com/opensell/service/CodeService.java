package com.opensell.service;

import org.springframework.stereotype.Service;

/**
 * @author Quoc Dung
 */

@Service
public class CodeService {
    public String generateCode() {
        return String.valueOf((int) (Math.random() * 999999-111111) + 111111);
    }
}
