package com.opensell.service.customermodification;

import com.opensell.exception.CustomerModificationException;

public interface ValueValidationCallable {
    boolean isValid() throws CustomerModificationException;
}
