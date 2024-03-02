package com.opensell.repository.adaptive.common;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
public class TableInfo {
    private int idValue;
    @Setter
    private String idColumnName;
    @Setter
    private List<String> noJdbcColumns;

    @Setter
    private String tableName;

    public void setIdValue(int idValue) {
        try {
            if(idValue >= 1) this.idValue = idValue;
            else throw new Exception("idValue need to be more than 1.");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public TableInfo(int idValue, String idColumnName, List<String> noJdbcColumns, String tableName) {
        setIdValue(idValue);
        setIdColumnName(idColumnName);
        setNoJdbcColumns(noJdbcColumns);
        setTableName(tableName);
    }
}