package com.marcs.app.user.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.marcs.app.user.client.domain.User;
import com.marcs.common.enums.AccountStatus;
import com.marcs.common.enums.WebRole;

import org.springframework.jdbc.core.RowMapper;

/**
 * Mapper class to map a User Profile Object {@link User}
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
public class UserProfileMapper implements RowMapper<User> {
	public static UserProfileMapper USER_MAPPER = new UserProfileMapper();

	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User();
		user.setId(rs.getInt("id"));
		user.setFirstName(rs.getString("first_name"));
		user.setLastName(rs.getString("last_name"));
		user.setEmail(rs.getString("email"));
		user.setWebRole(WebRole.getRole(rs.getInt("web_role_id")));
		user.setStoreId(rs.getString("store_id"));
		user.setStoreName(rs.getString("store_name"));
		user.setAppAccess(rs.getBoolean("app_access"));
		user.setEmailReportsEnabled(rs.getBoolean("email_reports_enabled"));
		user.setAccountStatus(AccountStatus.valueOf(rs.getString("account_status")));

		try {
			user.setPassword(rs.getString("password"));
		} catch (Exception e) {
			user.setPassword(null);
		}

		user.setLastLoginDate(rs.getTimestamp("last_login_date").toLocalDateTime());
		user.setHireDate(rs.getTimestamp("hire_date").toLocalDateTime());
		user.setInsertDate(rs.getTimestamp("insert_date").toLocalDateTime());
		return user;
	}
}
