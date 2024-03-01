package com.opensell.service.homesql;

public class AdaptiveQueryOption {
	public final static String ASSIGN = " %s = ?";
	//public final static String ASSIGN = " %s = :%s";
	
	public static String updateWhere(String table, String changement, String condition) {
		return String.format("UPDATE %s SET%s WHERE%s", table, changement, condition);
	}
	
	public static String firstAssign(String key) {
		return String.format(ASSIGN, key);
		//return String.format(ASSIGN, key, key);
	}
	
	public static String otherAssign(String key) {
		//return String.format(" AND"+ASSIGN, key, key);
		return String.format(" AND"+ASSIGN, key);
	}
}
