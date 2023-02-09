/**
 * Copyright of Marcs App. All rights reserved.
 */
package com.marcs.app.requestTracker.dao;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.marcs.app.requestTracker.client.domain.RequestType;
import com.marcs.app.requestTracker.client.domain.UserRequest;
import com.marcs.app.requestTracker.mapper.UserRequestMapper;
import com.marcs.common.abstracts.BaseDao;
import com.marcs.sql.SqlParamBuilder;

/**
 * Class that handles all the dao calls to the database for Request Tracker
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Repository
public class RequestTrackerDao extends BaseDao {

	public RequestTrackerDao(DataSource source) {
		super(source);
	}

	/**
	 * Gets list of user request for a user id.
	 * 
	 * @param userId of the user.
	 * @return {@link UserRequest}
	 */
	public List<UserRequest<Void>> getCurrentUserRequests(long userId) {
		MapSqlParameterSource params = parameterSource(USER_ID, userId);
		return getList("getUserRequest", params, getUserRequestMapper(Void.class));
	}

	/**
	 * Base get for user request to take in generic types of the user request.
	 * 
	 * @param id    The id of the request.
	 * @param type  The Request type.
	 * @param clazz The Request class.
	 * @return The User Request found.
	 */
	public <T> Optional<UserRequest<T>> getUserRequestById(long id, RequestType type, Class<T> clazz) {
		MapSqlParameterSource params = SqlParamBuilder.with().withParam(REQUEST_ID, id).withParam(TYPE, type).build();
		return getOptional("getUserRequest", params, getUserRequestMapper(clazz));
	}

	/**
	 * Gets the desired mapper based on the class type.
	 * 
	 * @param <T>   Generic class
	 * @param clazz The class to generate a mapper for.
	 * @return The UserRequestMapper
	 */
	private <T> UserRequestMapper<T> getUserRequestMapper(Class<T> clazz) {
		return new UserRequestMapper<T>(clazz);
	}
}
