package com.marcs.app.user.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.marcs.app.user.client.domain.Application;
import com.marcs.common.abstracts.AbstractMapper;

/**
 * Mapper class to map a Application Object {@link Application}
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
public class ApplicationMapper extends AbstractMapper<Application> {
	public static ApplicationMapper APPLICATION_MAPPER = new ApplicationMapper();

	public Application mapRow(ResultSet rs, int rowNum) throws SQLException {
		Application app = new Application();
		app.setId(rs.getInt(APP_ID));
		app.setName(rs.getString(APP_NAME));
		app.setAccess(rs.getBoolean(ACCESS));
		app.setEnabled(rs.getBoolean(ENABLED));
		return app;
	}
}