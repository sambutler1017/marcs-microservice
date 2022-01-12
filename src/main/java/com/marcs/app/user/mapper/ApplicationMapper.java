package com.marcs.app.user.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.marcs.app.user.client.domain.Application;

import org.springframework.jdbc.core.RowMapper;

/**
 * Mapper class to map a Application Object {@link Application}
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
public class ApplicationMapper implements RowMapper<Application> {
	public static ApplicationMapper APPLICATION_MAPPER = new ApplicationMapper();

	public Application mapRow(ResultSet rs, int rowNum) throws SQLException {
		Application app = new Application();
		app.setId(rs.getInt("app_id"));
		app.setName(rs.getString("app_name"));
		app.setAccess(rs.getBoolean("access"));
		app.setEnabled(rs.getBoolean("enabled"));
		return app;
	}
}