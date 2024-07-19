package com.opensell.service;

import com.opensell.repository.AdRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * @author Olivier Mansuy
 */
@Deprecated(forRemoval = true)
@Service
@RequiredArgsConstructor
public class LinkGenerator {
    private final AdRepository adRep;

    private static final Random random = new Random();
    private static final int LINK_LENGTH = 12;
    private static final Character[] NUMBER_ARRAY = {'1', '2', '3', '4', '5', '6', '7', '8', '9'};
    private static final byte LOWERCASE_START = 97;
    private static final byte LOWERCASE_END = 122;
    private final ArrayList<Character> charArray = getCharArray(true);

    private ArrayList<Character> getCharArray(boolean hasNumbers) {
        ArrayList<Character> array = new ArrayList<>();
        for (int elem = LOWERCASE_START; elem < LOWERCASE_END + 1; elem++) {
            array.add((char) elem);
            array.add((char) (elem - 32));
        }
        if (hasNumbers) array.addAll(Arrays.asList(NUMBER_ARRAY));
        return array;
    }

    private String generateLink() {
        StringBuilder result = new StringBuilder();
        for (int elem = 0; elem < LINK_LENGTH; elem++) result.append(charArray.get(random.nextInt(0, charArray.size())));
        return result.toString();
    }

    public String generateAdLink() {
        String link = generateLink();
        while (adRep.existsByLink(link)) link = generateLink();
        return link;
    }
}
