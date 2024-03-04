package com.opensell.repository.adaptive.common;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This interface is to put the function usable with other repository.
 * 
 * T can be useful for future update.
 * 
 * @author Achraf
*/
public interface AdaptiveRepository {
	public static List<String> getClassField(Class<?> entityClass) {
        var allColumns = new ArrayList<String>();

		for(Field field : entityClass.getDeclaredFields())
            allColumns.add(field.getName());

        return allColumns;
	}

	public UpdateResult updateWithId(Map<String, Object> json, TableInfo tableInfo, int idValue);
}