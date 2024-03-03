package com.opensell.repository.adaptive.common;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Class that will contain useful information about a table.
 * 
 * @author Achraf
*/
@Data @AllArgsConstructor
public class TableInfo {
    private String idColumnName;
    
    // List of the columns that you do not want to deal in JDBC.
    private List<String> noJdbcColumns;

    // List of could you could not update like an id.
    private List<String> notUpdatable;

    private String tableName;
}