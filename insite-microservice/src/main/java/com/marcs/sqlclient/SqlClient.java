package com.marcs.sqlclient;

import static com.globals.db.DatabaseGlobals.PASS;
import static com.globals.db.DatabaseGlobals.URL;
import static com.globals.db.DatabaseGlobals.USER;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class SqlClient {

	private static JdbcTemplate jdbcTemplateObject;
	private static DriverManagerDataSource source = new DriverManagerDataSource();

	public static <T> T getTemplate(String query, RowMapper<T> mapper) {
		setSource();
		return jdbcTemplateObject.query(query, mapper).get(0);
	}

	public static <T> List<T> getPage(String query, RowMapper<T> mapper) {
		setSource();
		return jdbcTemplateObject.query(query, mapper);
	}

	private static void setSource() {
		source.setDriverClassName("com.mysql.jdbc.Driver");
		source.setUrl(URL);
		source.setUsername(USER);
		source.setPassword(PASS);
		jdbcTemplateObject = new JdbcTemplate(source);
	}
}
