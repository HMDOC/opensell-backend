package com.opensell.repository.adaptive.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Entity that will contain the number of row updated and 
 * the seperation(dividedJson) of the original map into 3(jpaJson, filteredJson, cantUpdateJson).
 * 
 * @author Achraf
 */
@AllArgsConstructor @Getter
public class UpdateResult {
    private DividedJson dividedJson;
    private int updatedRow;
}