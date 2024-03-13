package com.opensell.repository.adaptive.common;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import com.opensell.repository.adaptive.common.SqlError.SqlErrorType;

/**
 * Spring will use this class to get the implementation of the function updateWithId because 
 * it is called AdaptiveRepositoryImpl.
 * 
 * @author Achraf
*/
public class AdaptiveRepositoryImpl implements AdaptiveRepository {
	@Autowired
	protected DataSource dataSource;

	@Autowired
	protected NamedParameterJdbcTemplate npj;

	//public static final String ASSIGN = " %s = ?";
	public final static String FIRST_ASSIGN = "{0} = :{1}";
	public final static String OTHER_ASSIGN = ", "+FIRST_ASSIGN;
	
	/**
	 * Create a update query in String format.
	 * 
	 * @param table The table name
	 * @param changement The assignement string(name = :name AND ...) for the new value
	 * @param condition The assignement that will contain the verification in the left
	 * @return The update query.
	 * @author Achraf
	*/
	public static String createUpdateQuery(String table, String changement, String condition) {
		return MessageFormat.format("UPDATE {0} SET {1} WHERE {2}", table, changement, condition);
	}
	
	/**
	 * Create the first assignement statement(id = :id).
	 * 
	 * @return A assignement(id = :id)
	 * @author Achraf
	*/
	public static String firstAssign(String key) {
		return MessageFormat.format(FIRST_ASSIGN, toSnakeCase(key), key);
	}
	
	/**
	 * Create the statement that will follow the first one.
	 * 
	 * @return A string with a assignement that we follow the first one(AND name = :name)
	 * @author Achraf
	*/
	public static String otherAssign(String key) {
		return MessageFormat.format(OTHER_ASSIGN, toSnakeCase(key), key);
	}

	/**
	 * Function that convert a string formated with camelCase to snake_case.
	 * 
	 * @param word The word to put in snake_case
	 * @return The representation of the word in snake_case
	 * @author Achraf
	 */
	public static String toSnakeCase(String word) {
		try {
			var stringBuilder = new StringBuilder(word);
			
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
	 * To create the assign part of SQL Query just for normal equal with a Set of Map keys. For Example :
	 * 
	 * if a had a list like this : <code>["id", "name", "title"]</code>, the result of the function will be :
	 * 
	 * <code>id = :id AND name = :name AND title = :title</code>
	 * 
	 * @param jsonKeys The keys of the Map you want to execute in a Query
	 * @return A string that represent all the assignement we want to do
	 * @author Achraf
	 */
	public static String createAllAssign(LinkedHashSet<String> jsonKeys) {
		var stringBuilder = new StringBuilder();
		
		stringBuilder.append(firstAssign(jsonKeys.removeFirst()));
		jsonKeys.forEach(key -> {
			stringBuilder.append(otherAssign(key));
		});

		return stringBuilder.toString();
	}

	/**
	 * Same as createAssign but in this one we do not deal with a Set, we only deal
	 * with one id.
	 * 
	 * @param idColumnName The name of the column of the id you want to verify in the final Query
	 * 
	 * @return The string that represent the id assignement(id = :id)
	 * @author Achraf
	 */
	public static String createIdAssign(String idColumnName) {
		return firstAssign(idColumnName);
	}

	/**
	 * The purpose of this function is to separe the Map into two part, one part
	 * will contain the part that we will update with Spring JDBC and the other
	 * will contain the part that Spring JPA will deal with.
	 * 
	 * 
	 * @param json The object received from frontend
	 * @param noJdbcColumns The column that we do not want to deal in JDBC
	 * 
	 * @return A class that contain the two Map
	 * @author Achraf
	*/
	public static DividedJson filterJson(Map<String, Object> json, TableInfo tableInfo) {
		var jpaJson = new LinkedHashMap<String, Object>();
		var filtredJson = new LinkedHashMap<String, Object>();
		var errorKeys = new ArrayList<SqlError>();

		json.forEach((key, value) -> {
			if(tableInfo.getFieldsName().contains(key)) {
				if(!tableInfo.getNotUpdatable().contains(key)) {
					if(tableInfo.getJpaOnly().contains(key)) jpaJson.put(key, value);
					else filtredJson.put(key, value);
				} 
				
				else {
					errorKeys.add(new SqlError(SqlErrorType.NOT_UPDATABLE, key));
				}
			} else {
				errorKeys.add(new SqlError(SqlErrorType.COL_DOES_NOT_EXIST, key));
			}
		});

		return new DividedJson(jpaJson, filtredJson, errorKeys);
	}

	/**
	 * This function execute an update with sql native code an a query will be generated
	 * with the JSON receive from the frontend.
	 * 
	 * @param json The object received from the frontend
	 * @param tableInfo Important information of a table
	 * @param idValue The id of the row you want to change.
	 * @author Achraf
	*/
	@Override
	public UpdateResult updateWithId(Map<String, Object> json, TableInfo tableInfo, int idValue) {
		DividedJson dividedJson = new DividedJson();

		try {
			dividedJson = filterJson(json, tableInfo);
			var filteredJson = dividedJson.getFilteredJson();
			
			if(filteredJson.size() != 0) {
				var query = createUpdateQuery(
					tableInfo.getTableName(),
					createAllAssign(new LinkedHashSet<>(filteredJson.keySet())),
					createIdAssign(tableInfo.getIdColumnName())
				);
				
				filteredJson.put(tableInfo.getIdColumnName(), idValue);
				System.out.println(query);

				return new UpdateResult(npj.update(query, filteredJson), dividedJson.getErrorKeys(), dividedJson.getJpaJson());
			}
			
			else {
				System.out.println("Map is empty, cannot make or execute AdaptiveQuery.");
				return new UpdateResult(0, dividedJson.getErrorKeys(), dividedJson.getJpaJson());
			}
		} catch (Exception e) {
			try {
				System.out.println(e.getCause().getMessage());
				SqlError.getErrorFromException(dividedJson.getErrorKeys(), e.getCause().getMessage());
				return new UpdateResult(0, dividedJson.getErrorKeys(), null);
			} catch(Exception e2) {
				return new UpdateResult(0, dividedJson.getErrorKeys(), null);
			}
		}
	}
}
