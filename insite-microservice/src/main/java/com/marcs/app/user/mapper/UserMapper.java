package com.marcs.app.user.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.marcs.app.user.client.domain.User;
import com.marcs.service.enums.StoreRegion;
import com.marcs.service.enums.WebRole;

import org.springframework.jdbc.core.RowMapper;

/**
 * Mapper class to map a User Profile Object {@link User}
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
public class UserMapper implements RowMapper<User> {
	public static UserMapper USER_MAPPER = new UserMapper();

	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User();
		user.setId(rs.getInt("id"));
		user.setFirstName(rs.getString("first_name"));
		user.setLastName(rs.getString("last_name"));
		user.setEmail(rs.getString("email"));
		user.setWebRole(WebRole.valueOf(rs.getString("web_role")));
		user.setStoreRegion(StoreRegion.valueOf(rs.getString("region")));
		user.setAppAccess(rs.getInt("app_access"));
		user.setUsername(rs.getString("username"));
		user.setPassoword(rs.getString("password"));
		return user;
	}
}
