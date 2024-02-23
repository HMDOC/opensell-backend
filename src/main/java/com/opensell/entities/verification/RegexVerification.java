package com.opensell.entities.verification;

import java.util.regex.Pattern;

public enum RegexVerification {
    //lookahead for 64 characters (between a to z, A to Z and 0 to 9) until @ while preveting "." before it
    EMAIL("^(?=.{1,64}@)[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9]+)*@[a-z]+(\\.[a-zA-Z]+)+[a-zA-Z]$"),
    NAME("^([a-zA-Z]){1,15}((-|\\s)[a-zA-Z]{1,15}){0,2}")
    ;

    private String value;

    public String getValue() {
        return value;
    }

    private RegexVerification(String value) {
        this.value = value;
    }

    public boolean verify(String attribute) {
        return Pattern.compile(this.value).matcher(attribute).matches();
    }
}
