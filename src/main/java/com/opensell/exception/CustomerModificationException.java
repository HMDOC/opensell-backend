package com.opensell.exception;

public class CustomerModificationException extends Exception {
    public CustomerModificationException(String message) {
        super(message);
    }

    public static CustomerModificationException formattingException() {
        return new CustomerModificationException("WRONG FORMAT");
    }
}
