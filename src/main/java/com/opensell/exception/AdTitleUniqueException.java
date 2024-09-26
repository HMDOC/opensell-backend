package com.opensell.exception;

public class AdTitleUniqueException extends RuntimeException {
    public AdTitleUniqueException() {
        super("Ad title already exists");
    }
}
