package com.marcs.app.user.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.marcs.app.user.client.domain.UserCredentials;

/**
 * Mapper class to map a User Credentials Object {@link UserCredentials}
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
public class UserCredentialsMapper implements RowMapper<UserCredentials> {
	public static UserCredentialsMapper USER_CREDENTIALS_MAPPER = new UserCredentialsMapper();

	public UserCredentials mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserCredentials user = new UserCredentials();
		user.setId(rs.getInt("user_id"));
		user.setUsername(rs.getString("username"));
		user.setPassoword(rs.getString("password"));
		return user;
	}
}
