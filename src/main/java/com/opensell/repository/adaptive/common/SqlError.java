package com.opensell.repository.adaptive.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Class that help send error to frontend.
 * 
 * @author Achraf
*/
@Getter @AllArgsConstructor
public class SqlError {
    private SqlErrorType error;
    private String column;

    public enum SqlErrorType {
        COL_DOES_NOT_EXIST,
        NOT_UPDATABLE,
        UNIQUE_FAILED
    }
}
