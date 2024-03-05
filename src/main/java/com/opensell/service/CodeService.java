package com.opensell.service;

import org.springframework.stereotype.Service;

/**
 * This service is used to generate a random code used for the signup verification 
 * @author Quoc Dung
 */

@Service
public class CodeService {
    public String generateCode() {
        return String.valueOf((int) (Math.random() * 999999-111111) + 111111);
    }
}
