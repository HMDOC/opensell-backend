package com.opensell.repository.adaptive.common;

import java.util.List;

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

    public static void getErrorFromException(List<SqlError> sqlErrors, String exception) throws Exception {
        if(exception.contains("Duplicate entry")) {
            sqlErrors.add(new SqlError(SqlErrorType.UNIQUE_FAILED, exception.split(" ")[6]));
        }
    }
}
