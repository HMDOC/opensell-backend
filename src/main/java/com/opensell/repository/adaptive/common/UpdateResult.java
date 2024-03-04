package com.opensell.repository.adaptive.common;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Class that will contain the number of row updated and a list of errors.
 * 
 * @author Achraf
 */
@Getter @AllArgsConstructor @NoArgsConstructor
public class UpdateResult {
    private int updatedRow;
    private List<SqlError> errorKeys;
    private String exception;
}