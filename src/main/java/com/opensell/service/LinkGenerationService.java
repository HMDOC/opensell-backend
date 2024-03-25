package com.opensell.service;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class LinkGenerationService {

    private static final Random random = new Random();
    public static final byte FIRST_LOWER_LETTER = 97;
    public static final byte LAST_LOWER_LETTER = 122;
    public static final byte FIRST_UPPER_LETTER = 65;
    public static final byte LAST_UPPER_LETTER = 90;

    private char[] getCharArray() {
        return null;
    }

    public static String generateAdLink() {
        return null;
    }

    public static String generateCustomerLink() {
        return null;
    }
}
