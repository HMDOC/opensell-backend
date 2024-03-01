package com.opensell.service.homesql;

import static com.opensell.service.homesql.AdaptiveQueryOption.firstAssign;
import static com.opensell.service.homesql.AdaptiveQueryOption.otherAssign;
import static com.opensell.service.homesql.AdaptiveQueryOption.updateWhere;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.sql.DataSource;
import org.hibernate.type.SqlTypes;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;
import jakarta.transaction.Transactional;

@Component
@Transactional
public class AdaptiveQuery {
	/**
	 * Function that convert a string formated with camelCase to snake_case.
	 * 
	 * @author Achraf
	 */
	public static String toSnakeCase(String word) {
		try {
			StringBuilder stringBuilder = new StringBuilder(word);
			
			for (int i = 0; i < stringBuilder.length(); i++) {
				char currentCharachter = stringBuilder.charAt(i);

				// If lower case spotted
				if (Character.isUpperCase(currentCharachter)) {
					// set the value of the currentCharacter in the string to _
					stringBuilder.setCharAt(i, '_');

					// Add the current character in lower case after the _ in lower case
					stringBuilder.insert(i + 1, Character.toLowerCase(currentCharachter));
				}
			}

			return stringBuilder.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Take keys from a map and create a string that will contain the comparison or
	 * assignment like this: id = :id AND name = :name
	 * 
	 * 
	 * @author Achraf
	 */
	public static String extractMapKeys(Map<String, Object> entityInstance) {
		try {
			StringBuilder stringBuilder = new StringBuilder();
			// Get the keys of the map
			LinkedHashSet<String> keys = new LinkedHashSet<String>(entityInstance.keySet());

			// Deal with the first key and remove it from original list because we need to
			// add "AND" for the other
			//stringBuilder.append(firstAssign(toSnakeCase(keys.getFirst())));
			keys.removeFirst();

			// Put the key name on the final string
			keys.forEach(key -> {
				//stringBuilder.append(otherAssign(toSnakeCase(key)));
			});

			return stringBuilder.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * This will create the adaptive query.
	 *
	 * 
	 * @author Achraf
	 */
	public static String updateQuery(String table, Map<String, Object> valuesToChange,
			Map<String, Object> conditionValues) {
		try {
			return updateWhere(table, extractMapKeys(valuesToChange), extractMapKeys(conditionValues));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * To iterate over a map to add is key and value in a MapSqlParameterSource and
	 * to create a String that is a part of a prepared query :
	 * 
	 * "name = :name AND id = :id"
	 * 
	 * @author Achraf
	 */
	public static String createAssign(LinkedHashSet<String> keys) {
		StringBuilder stringBuilder = new StringBuilder();
		int index = 1;
		// To deal with the first element because after we need to put AND in the Query if other element are there.
		stringBuilder.append(firstAssign(toSnakeCase(keys.removeFirst())));
		
		for (String key : keys) {
			index++;
			stringBuilder.append(otherAssign(toSnakeCase(key)));
		}
		
		return stringBuilder.toString();
	}

	/**
	 * Get the sql type of an object.
	 * 
	 * @author Achraf
	 */
	public static int getSqlType(Object object) {
		if(object instanceof Boolean) return SqlTypes.BOOLEAN;
		else if(object instanceof String) return SqlTypes.VARCHAR;
		else if(object instanceof Integer) return SqlTypes.INTEGER;
		else if(object instanceof Float) return SqlTypes.FLOAT;
		else if(object instanceof Double) return SqlTypes.DOUBLE;
		else return SqlTypes.NULL;
	}
	
	public static void addKeysAndValues(Map<String, ?> json, MapSqlParameterSource valuesDefinition) {
		Set<String> keys = json.keySet();
		
		keys.forEach(key -> {
			Object value = json.get(key);
			valuesDefinition.addValue(key, value, getSqlType(value));
		});
	}
	
	/**
	 * The function that will generate and update query.
	 * 
	 * @author Achraf
	 */
	public static int adaptiveUpdate(String table, Map<String, Object> valuesToChange, Map<String, Object> conditionValues, DataSource dataSource) {
		try {
			SqlParameterSource parameters = new MapSqlParameterSource();
			NamedParameterJdbcTemplate nPJT = new NamedParameterJdbcTemplate(dataSource);
			JdbcTemplate jdbc = new JdbcTemplate(dataSource);
			
			// Name et le type: 
			//new SqlParameter(table, 0);
			
			addKeysAndValues(valuesToChange, (MapSqlParameterSource) parameters);
			addKeysAndValues(conditionValues,(MapSqlParameterSource) parameters);

			String query = AdaptiveQueryOption.updateWhere(table, 
					createAssign(new LinkedHashSet<>(valuesToChange.keySet())), 
					createAssign(new LinkedHashSet<>(conditionValues.keySet())));
			
			System.out.println(query);
			System.out.println(parameters);
			
			Map<String, Object> l = new LinkedHashMap<>();
			l.put("is_deleted", true);
			l.put("title", "Hamidous");
			l.put("id_ad", 1);
			
			System.out.println(query);
			
			return jdbc.update(query, true, "dasf", 1);
			//return jdbcTemplate.update(query, new Object[] {new SqlParameterValue(getSqlType(true), "is_deleted", true), new SqlParameterValue(getSqlType(""), "title", "Hamid Di Dis Di Dous"), new SqlParameterValue(getSqlType(1), "id_ad", 1111)});
			//return jdbcTemplate.update(query, new Object[] {true, "Hamid Di Dis Di Dous", 11}, new int[] {SqlTypes.TINYINT, SqlTypes.VARCHAR, SqlTypes.INTEGER});
		} catch (DataAccessException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public static void addToStatement(PreparedStatement ps, int startIndex, Map<String, Object> json) {
		try {
			List<String> keys = new ArrayList<>(json.keySet());
			
			for(int i = startIndex; i < keys.size(); i++) {
				ps.setObject(i+1, json.get(keys.get(i)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * The function that will generate and update query.
	 * 
	 * @author Achraf
	 */
	public static int adaptiveUpdate2(String table, Map<String, Object> valuesToChange, Map<String, Object> conditionValues, DataSource dataSource) {
		Connection con = null;
		PreparedStatement ps = null;
		
		try {
			String query = AdaptiveQueryOption.updateWhere(table, 
					createAssign(new LinkedHashSet<>(valuesToChange.keySet())), 
					createAssign(new LinkedHashSet<>(conditionValues.keySet())));
			
			con = dataSource.getConnection();
			ps = con.prepareStatement(query);
			
			addToStatement(ps, 0, valuesToChange);
			ps.setObject(3, conditionValues.values().toArray()[0]);
			//addToStatement(ps, valuesToChange.size() - 1, conditionValues);
			
			return ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
		finally {
			try {
				if(con != null) con.close();
				if(ps != null) ps.close();
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
		}
	}

	public static void main(String[] args) {
		try {
			Map<String, Object> map = new LinkedHashMap<>();
			map.put("isDeleted", true);
			map.put("title", "Hamid Di Dis Di Dous");

			Map<String, Object> cond = new LinkedHashMap<>();
			cond.put("idAd", 1);
			
			//System.out.println(test("car", map, cond));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}