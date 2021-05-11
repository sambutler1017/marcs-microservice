package com.marcs.app.user.dao;

import static com.marcs.app.user.mapper.UserMapper.USER_MAPPER;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Sets;
import com.marcs.app.user.client.domain.User;
import com.marcs.app.user.client.domain.request.UserGetRequest;
import com.marcs.service.sql.SqlBuilder;
import com.marcs.service.sql.SqlClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Class that handles all the dao calls to the database for users
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Component
public class UserDAO {

	@Autowired
	private SqlClient sqlClient;
	
	@Autowired
	private SqlBuilder sqlBuilder;

	/**
	 * Get users based on given request filter
	 * 
	 * @param request of the user
	 * @return User profile object {@link User}
	 */
	public User getUsers(UserGetRequest request) {
		Map<String, Set<?>> params = new HashMap<>();
		params.put("username", request.getUsername());

		sqlBuilder.setQueryFile("userDAO");
		sqlBuilder.setParams(params);

		return sqlClient.getPage(sqlBuilder.getSql("getUsers"), USER_MAPPER).get(0);
	}

	/**
	 * This method returns a user profile object containing profile type information
	 * about the user
	 * 
	 * @param id of the user
	 * @return User profile object {@link UserProfile}
	 */
	public User getUserById(int id) {
		Map<String, Set<?>> params = new HashMap<>();
		params.put("userId", Sets.newHashSet(id));

		sqlBuilder.setQueryFile("userDAO");
		sqlBuilder.setParams(params);

		return sqlClient.getTemplate(sqlBuilder.getSql("getUserById"), USER_MAPPER);
	}
}
