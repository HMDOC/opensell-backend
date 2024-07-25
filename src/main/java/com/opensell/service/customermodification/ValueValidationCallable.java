package com.opensell.service.customermodification;

import com.opensell.exception.CustomerModificationException;

@Deprecated(forRemoval = true)
public interface ValueValidationCallable {
    boolean isValid() throws CustomerModificationException;
}
