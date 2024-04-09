package com.opensell.service;


import com.opensell.repository.AdRepository;
import com.opensell.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * @author Olivier Mansuy
 * //    private static final byte UPPERCASE_START = 65;
 * //    private static final byte UPPERCASE_END = 90;
 * //boolean hasSpecialCharacters
 */
@Component
public class LinkGenerator {

    private static final Random random = new Random();
    private final int linkLength;
    private ArrayList<Character> charArray;
    private static final Character[] NUMBER_ARRAY = {'1', '2', '3', '4', '5', '6', '7', '8', '9'};
    private static final byte LOWERCASE_START = 97;
    private static final byte LOWERCASE_END = 122;

    @Autowired
    private AdRepository adRep;

    @Autowired
    private CustomerRepository customerRep;

    private ArrayList<Character> getCharArray(boolean hasNumbers) {
        ArrayList<Character> array = new ArrayList<>();
        for (int elem = LOWERCASE_START; elem < LOWERCASE_END + 1; elem++) {
            array.add((char) elem);
            array.add((char) (elem - 32));
        }
        if (hasNumbers) array.addAll(Arrays.asList(NUMBER_ARRAY));
        return array;
    }

    public LinkGenerator() {
        this.linkLength = 12;
        this.charArray = getCharArray(true);
    }

    private String generateLink() {
        StringBuilder result = new StringBuilder();
        for (int elem = 0; elem < linkLength; elem++) result.append(charArray.get(random.nextInt(0, charArray.size())));
        return result.toString();
    }

    public String generateCustomerLink() {
        String link = generateLink();
        while (customerRep.existsByLink(link)) link = generateLink();
        return link;
    }

    public String generateAdLink() {
        String link = generateLink();
        while (adRep.existsByLink(link)) link = generateLink();
        return link;
    }
}
