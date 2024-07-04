package com.opensell.service.customerModification;

import com.opensell.exception.CustomerModificationException;

public interface ValueValidationCallable {
    boolean isValid() throws CustomerModificationException;
}
