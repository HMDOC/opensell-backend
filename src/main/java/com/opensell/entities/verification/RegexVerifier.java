package com.opensell.entities.verification;

import java.util.regex.Pattern;

public enum RegexVerifier {

    EMAIL("^(?=.{1,64}@)[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9]+)*@[a-z]+(\\.[a-zA-Z]+)+[a-zA-Z]$"),
    FIRST_LAST_NAME("^([a-zA-Z]){1,15}((-|\\s)[a-zA-Z]{1,15}){0,2}"),
    PHONE_NUMBER("^\\d{3}-\\d{3}-\\d{4}$"),
    USERNAME("^([a-zA-Z0-9_]){1,30}$"),
    PWD("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*\\W).{8,}"),
    URL("(https?:\\/\\/)?(www\\.)?(?=[a-zA-Z0-9]{1,256}\\.)[a-zA-Z0-9]+(\\.[a-z]{2,5}){1,3}(\\/[a-zA-Z0-9-\\._~:\\/?#\\[\\]@!$&'\\(\\)\\*\\+,;=]*)?"),
    ;

    private final String value;

    private RegexVerifier(String value) {
        this.value = value;
    }

    public boolean verify(String attribute) {
        return Pattern.compile(this.value).matcher(attribute).matches();
    }
}
