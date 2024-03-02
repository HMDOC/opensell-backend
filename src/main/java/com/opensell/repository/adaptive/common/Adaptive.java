package com.opensell.repository.adaptive.common;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Function;
import javax.sql.DataSource;
import org.glassfish.jaxb.runtime.api.RawAccessor;
import org.hibernate.mapping.List;
import org.hibernate.mapping.Map;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author Achraf
 */
public class Adaptive {
	@Autowired
	protected DataSource dataSource;

	//public static final String ASSIGN = " %s = ?";
	public final static String FIRST_ASSIGN = "{0} = :{0}";
	public final static String OTHER_ASSIGN = " AND {0} = :{0}";
	
	public static String updateWhere(String table, String changement, String condition) {
		return MessageFormat.format("UPDATE {0} SET {1} WHERE {2}", table, changement, condition);
	}
	
	public static String firstAssign(String key) {
		return MessageFormat.format(FIRST_ASSIGN, key);
	}
	
	public static String otherAssign(String key) {
		return MessageFormat.format(OTHER_ASSIGN, key);
	}

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
	 * To create the assign part of SQL Query just for normal equal with a Set of Map keys. For Example :
	 * 
	 * if a had a list like this : <code>["id", "name", "title"]</code>, the result of the function will be :
	 * 
	 * <code>id = :id AND name = :name AND title = :title</code>
	 * 
	 * @param jsonKeys The keys of the Map you want to execute in a Query.
	 * @author Achraf
	 */
	public static String createAssign(LinkedHashSet<String> jsonKeys) {
		StringBuilder stringBuilder = new StringBuilder();
		
		stringBuilder.append(firstAssign(toSnakeCase(jsonKeys.removeFirst())));
		jsonKeys.forEach(key -> {
			stringBuilder.append(otherAssign(toSnakeCase(key)));
		});

		return stringBuilder.toString();
	}

	public static String createIdAssign(AdaptiveId adaptiveId) {

		
	}

	public static void main(String[] args) {
		HashSet<String> jsonKeys = new HashSet<>();
		jsonKeys.add("CamelCase");
		jsonKeys.add("hamidCaseTwo");

		//jsonKeys.stream().map(a, b -> {

		//});

		Function<String, Integer> french = a -> a.length();
		/*System.out.println(
			french.apply("1234")
		);*/
		
		/*var allo = "1231234";
		System.out.println(allo.length());
		french = french.andThen(a -> a * 1000);*/

		/*switch (2) {
			case 2 -> {
				System.out.println("1234");
				System.out.println("Case 2.");
			}
			case 1 -> System.out.println("Hamid!");
			default -> System.out.println("Default");
		}*/

		var hamid = "1223";
		System.out.println(hamid.length());

		/*
		System.out.println(
			french.apply("1234")
		); 
		*/
		//System.out.println("%s".formatted("Allo"));
		//System.out.println(MessageFormat.format("Bonjour {0} {1}, il faut faire le devoir pour {0}", "Hamid", "Boumedhi"));
	}
}
