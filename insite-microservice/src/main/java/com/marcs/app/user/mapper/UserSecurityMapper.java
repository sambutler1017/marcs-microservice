package com.marcs.app.user.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.marcs.app.user.client.domain.UserSecurity;

/**
 * Mapper class to map a User Security Object {@link UserSecurity}
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
public class UserSecurityMapper implements RowMapper<UserSecurity> {
	public static UserSecurityMapper USER_SECURITY_MAPPER = new UserSecurityMapper();

	public UserSecurity mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserSecurity user = new UserSecurity();
		user.setId(rs.getInt("user_id"));
		user.setAnswer(rs.getString("answer"));
		user.setQuestion(rs.getString("security_question"));
		return user;
	}
}
