package com.marcs.app.user.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.marcs.app.user.client.domain.User;
import com.marcs.common.abstracts.AbstractMapper;
import com.marcs.common.enums.AccountStatus;
import com.marcs.common.enums.WebRole;

/**
 * Mapper class to map a User Profile Object {@link User}
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
public class UserProfileMapper extends AbstractMapper<User> {
	public static UserProfileMapper USER_MAPPER = new UserProfileMapper();

	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User();
		user.setId(rs.getInt(ID));
		user.setFirstName(rs.getString(FIRST_NAME));
		user.setLastName(rs.getString(LAST_NAME));
		user.setEmail(rs.getString(EMAIL));
		user.setWebRole(WebRole.getRole(rs.getInt(WEB_ROLE_ID)));
		user.setStoreId(rs.getString(STORE_ID));
		user.setStoreName(rs.getString(STORE_NAME));
		user.setAppAccess(rs.getBoolean(APP_ACCESS));
		user.setEmailReportsEnabled(rs.getBoolean(EMAIL_REPORTS_ENABLED));
		user.setAccountStatus(AccountStatus.valueOf(rs.getString(ACCOUNT_STATUS)));

		try {
			user.setPassword(rs.getString(PASSWORD));
		}
		catch(Exception e) {
			user.setPassword(null);
		}

		user.setLastLoginDate(dateTimeFormat(rs.getString(LAST_LOGIN_DATE)));
		user.setHireDate(dateTimeFormat(rs.getString(HIRE_DATE)));
		user.setInsertDate(dateTimeFormat(rs.getString(INSERT_DATE)));
		return user;
	}
}
