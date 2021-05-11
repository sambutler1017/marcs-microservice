package com.marcs.app.user.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.globals.enums.WebRole;
import com.marcs.app.user.client.domain.UserProfile;

/**
 * Mapper class to map a User Profile Object {@link UserProfile}
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
public class UserProfileMapper implements RowMapper<UserProfile> {
	public static UserProfileMapper USER_PROFILE_MAPPER = new UserProfileMapper();

	public UserProfile mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserProfile user = new UserProfile();
		user.setId(rs.getInt("user_id"));
		user.setFirstName(rs.getString("first_name"));
		user.setLastName(rs.getString("last_name"));
		user.setEmail(rs.getString("email"));
		user.setWebRole(WebRole.valueOf(rs.getString("role_type")));
		user.setStoreRegion(rs.getString("region"));
		user.setAppAccess(rs.getInt("app_access"));
		return user;
	}
}
