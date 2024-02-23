package com.opensell.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Quoc Dung
 */

@CrossOrigin("http://localhost/")
@Service
@RestController
public class CodeService {
    public int generateCode() {
        return (int) (Math.random() * 999999-111111) + 111111;
    }

    // public static boolean checkCode(int code) {
    //     if 
    // }
}
