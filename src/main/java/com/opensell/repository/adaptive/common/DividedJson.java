package com.opensell.repository.adaptive.common;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class that contain the Map(jpaJson) that JPA will use to make the other update, the Map(filteredJson) we will
 * use with JDBC and the Map(cantUpdateJson) contain the key that we cannot update.
 * 
 * @author Achraf
*/
@Data @AllArgsConstructor @NoArgsConstructor
public class DividedJson {
    // The Map that we will deal in JPA
    private Map<String, Object> jpaJson;

    // The Map with all the attributs that JDBC can use
    private Map<String, Object> filteredJson;

    // The attribute that cannot be updated
    private Map<String, Object> cantUpdateJson;
}