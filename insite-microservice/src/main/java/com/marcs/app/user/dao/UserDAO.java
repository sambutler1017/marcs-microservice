package com.marcs.app.user.dao;

import static com.marcs.app.user.mapper.UserCredentialsMapper.USER_CREDENTIALS_MAPPER;
import static com.marcs.app.user.mapper.UserProfileMapper.USER_PROFILE_MAPPER;
import static com.marcs.app.user.mapper.UserSecurityMapper.USER_SECURITY_MAPPER;
import static com.marcs.sqlclient.SqlClient.getTemplate;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.marcs.app.user.client.domain.UserCredentials;
import com.marcs.app.user.client.domain.UserProfile;
import com.marcs.app.user.client.domain.UserSecurity;
import com.marcs.app.user.client.domain.request.UserCredentialsGetRequest;
import com.sql.service.parser.SqlBuilder;

/**
 * Class that handles all the dao calls to the database for users
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Component
public class UserDAO {

	private SqlBuilder sqlBuilder = new SqlBuilder();

	/**
	 * This method returns a user profile object containing profile type information
	 * about the user
	 * 
	 * @param id of the user
	 * @return User profile object {@link UserProfile}
	 * 
	 * @author Sam Butler
	 * @since May 13, 2020
	 */
	public UserProfile getUserById(int id) {
		Map<String, String> params = new HashMap<>();
		params.put("userId", String.valueOf(id));

		sqlBuilder.setQueryFile("userDAO");
		sqlBuilder.setParams(params);

		return getTemplate(sqlBuilder.getSql("getUserById"), USER_PROFILE_MAPPER);
	}

	/**
	 * This method returns a user credentials object containing the login
	 * information for the user
	 * 
	 * @param id of the user
	 * @return User Credentials object {@link UserCredentials}
	 * 
	 * @author Sam Butler
	 * @since May 13, 2020
	 */
	public UserCredentials getUserCredentials(UserCredentialsGetRequest request) {
		Map<String, String> params = new HashMap<>();

		if (request.getUserId() != -1)
			params.put("userId", String.valueOf(request.getUserId()));
		else
			params.put("name", request.getUsername());

		sqlBuilder.setQueryFile("userDAO");
		sqlBuilder.setParams(params);

		return getTemplate(sqlBuilder.getSql("getUserCredentials"), USER_CREDENTIALS_MAPPER);
	}

	/**
	 * This method returns a user security object given the id of the user
	 * 
	 * @param id of the user
	 * @return User Security object {@link UserSecurity}
	 * 
	 * @author Sam Butler
	 * @since May 13, 2020
	 */
	public UserSecurity getUserSecurity(int id) {
		Map<String, String> params = new HashMap<>();
		params.put("userId", String.valueOf(id));

		sqlBuilder.setQueryFile("userDAO");
		sqlBuilder.setParams(params);

		return getTemplate(sqlBuilder.getSql("getUserSecurity"), USER_SECURITY_MAPPER);
	}
}
