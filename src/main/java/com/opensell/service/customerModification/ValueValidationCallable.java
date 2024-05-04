package com.opensell.service.customerModification;

public interface ValueValidationCallable {
    public abstract boolean isValid() throws CustomerModificationException;
}
