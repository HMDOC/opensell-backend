package com.opensell.repository.adaptive.common;

import java.util.LinkedHashSet;
import java.util.Map;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * This interface is to put the function usable with other repository.
 * 
 * T can be useful for future update.
 * 
 * @author Achraf
*/
public interface AdaptiveRepository<T> {
	public int updateWithId(Map<String, Object> json, TableInfo tableInfo);
}

/**
 * Spring will use this class to get the implementation of the function updateWithId 
 * 
 * @author Achraf
 * @deprecated
*/
class AdaptiveRepositoryImpl<T> extends Adaptive implements AdaptiveRepository<T> {
	/**
	 * This function execute an update with sql native code an a query will be generated
	 * with the JSON receive from the frontend.
	 * 
	 * @param json The object received from the frontend
	 * @param tableInfo Important information of a table
	 * @author Achraf
	*/
	@Override
	public int updateWithId(Map<String, Object> json, TableInfo tableInfo) {
		try {
			NamedParameterJdbcTemplate npj = new NamedParameterJdbcTemplate(super.dataSource);
			DividedJson dividedJson = super.filterJson(json, tableInfo.getNoJdbcColumns());
			Map<String, Object> filteredJson = dividedJson.getFilteredJson();
			
			String query = super.updateWhere(
				tableInfo.getTableName(),
				super.createAssign(new LinkedHashSet<>(filteredJson.keySet())),
				super.createIdAssign(tableInfo.getIdColumnName())
			);
			
			filteredJson.put(tableInfo.getIdColumnName(), tableInfo.getIdValue());
			System.out.println(query);

			return npj.update(query, filteredJson);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}