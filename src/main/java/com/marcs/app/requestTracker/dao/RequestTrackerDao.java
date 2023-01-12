package com.marcs.app.requestTracker.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.marcs.app.requestTracker.client.domain.RequestType;
import com.marcs.app.requestTracker.client.domain.UserRequest;
import com.marcs.app.requestTracker.mapper.UserRequestMapper;
import com.marcs.app.store.client.domain.Store;
import com.marcs.app.vacation.client.domain.Vacation;
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

	@Autowired
	public RequestTrackerDao(DataSource source) {
		super(source);
	}

	/**
	 * Gets list of user request for a user id.
	 * 
	 * @param userId of the user.
	 * @return {@link UserRequest}
	 */
	public List<UserRequest<Void>> getCurrentUserRequests(long userId) throws Exception {
		MapSqlParameterSource params = parameterSource(USER_ID, userId);
		return getPage(getSql("getUserRequest", params), params, getUserRequestMapper(Void.class));
	}

	/**
	 * Gets the user vacation request by the request id.
	 * 
	 * @param id of the request
	 * @return {@link UserRequest}
	 */
	public UserRequest<Vacation> getUserVacationRequestById(long id) throws Exception {
		return getUserRequestById(id, RequestType.VACATION, Vacation.class);
	}

	/**
	 * Gets the user vacation request by the request id.
	 * 
	 * @param id of the request
	 * @return {@link UserRequest}
	 */
	public UserRequest<Store> getUserStoreRequestById(long id) throws Exception {
		return getUserRequestById(id, RequestType.STORE, Store.class);
	}

	/**
	 * Base get for user request to take in generic types of the user request.
	 * 
	 * @param id     The id of the request.
	 * @param type   The Request type.
	 * @param mapper The Request Tracker Mapper.
	 * @return The User Request found.
	 */
	private <T> UserRequest<T> getUserRequestById(long id, RequestType type, Class<T> clazz) {
		MapSqlParameterSource params = SqlParamBuilder.with().withParam(REQUEST_ID, id).withParam(TYPE, type).build();
		return get(getSql("getUserRequest", params), params, getUserRequestMapper(clazz));
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
