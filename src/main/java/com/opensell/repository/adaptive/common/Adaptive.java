package com.opensell.repository.adaptive.common;

import java.text.MessageFormat;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This is a class that contain function to help create adaptive query.
 * 
 * @author Achraf
 */
public class Adaptive {
	@Autowired
	protected DataSource dataSource;

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
	public static String updateWhere(String table, String changement, String condition) {
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
	public static String createAssign(LinkedHashSet<String> jsonKeys) {
		StringBuilder stringBuilder = new StringBuilder();
		
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
	public static DividedJson filterJson(Map<String, Object> json, List<String> noJdbcColumns, List<String> notUpdatable) {
		Map<String, Object> jpaJson = new LinkedHashMap<>();
		Map<String, Object> filtredJson = new LinkedHashMap<>();
		Map<String, Object> cantUpdateJson = new LinkedHashMap<>();

		json.forEach((key, value) -> {
			if(noJdbcColumns.contains(key)) jpaJson.put(key, value);
			else if(notUpdatable.contains(key)) cantUpdateJson.put(key, value);
			else filtredJson.put(key, value);
		});

		return new DividedJson(jpaJson, filtredJson, cantUpdateJson);
	}
}
