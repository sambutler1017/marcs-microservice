/**
 * Copyright of Marcs App. All rights reserved.
 */
package com.marcs.app.requestTracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.webjars.NotFoundException;

import com.marcs.app.requestTracker.client.domain.RequestType;
import com.marcs.app.requestTracker.client.domain.UserRequest;
import com.marcs.app.requestTracker.dao.RequestTrackerDao;
import com.marcs.app.store.client.domain.Store;
import com.marcs.app.vacation.client.domain.Vacation;
import com.marcs.jwt.utility.JwtHolder;

/**
 * Request Tracker class that handles all service calls to the dao
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Component
public class RequestTrackerService {

	@Autowired
	private RequestTrackerDao dao;

	@Autowired
	private JwtHolder jwtHolder;

	/**
	 * Gets list of user request for a user id.
	 * 
	 * @return {@link UserRequest}
	 */
	public List<UserRequest<Void>> getCurrentUserRequests() throws Exception {
		return dao.getCurrentUserRequests(jwtHolder.getUserId());
	}

	/**
	 * Gets the user vacation request by the request id.
	 * 
	 * @param id of the request
	 * @return {@link UserRequest}
	 */
	public UserRequest<Vacation> getUserVacationRequestById(long id) throws Exception {
		return dao.getUserRequestById(id, RequestType.VACATION, Vacation.class)
				.orElseThrow(() -> new NotFoundException("Vacation Request not found for id: " + id));
	}

	/**
	 * Gets the user vacation request by the request id.
	 * 
	 * @param id of the request
	 * @return {@link UserRequest}
	 */
	public UserRequest<Store> getUserStoreRequestById(long id) throws Exception {
		return dao.getUserRequestById(id, RequestType.STORE, Store.class)
				.orElseThrow(() -> new NotFoundException("Store Request not found for id: " + id));
	}
}
