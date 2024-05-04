package com.opensell.service.customerModification;

public class CustomerModificationException extends Exception {
    public CustomerModificationException(String message) {
        super(message);
    }

    public static CustomerModificationException formattingException() {
        return new CustomerModificationException("WRONG FORMAT");
    }
}
