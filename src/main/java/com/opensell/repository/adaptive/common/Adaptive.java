package com.opensell.repository.adaptive.common;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author Achraf
 */
public class Adaptive {
	@Autowired
	public DataSource dataSource;
}
