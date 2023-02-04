/**
 * Copyright of Marcs App. All rights reserved.
 */
package com.marcs.app.requestTracker.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.marcs.annotations.interfaces.RestApiController;
import com.marcs.app.requestTracker.client.domain.UserRequest;
import com.marcs.app.requestTracker.service.RequestTrackerService;
import com.marcs.app.store.client.domain.Store;
import com.marcs.app.vacation.client.domain.Vacation;

@RestApiController
@RequestMapping("api/request-tracker-app/request")
public class RequestTrackerController {

	@Autowired
	private RequestTrackerService requestTrackerService;

	/**
	 * Gets list of user request for a user id.
	 * 
	 * @param userId of the user.
	 * @return {@link UserRequest}
	 */
	@GetMapping("/user")
	public List<UserRequest<Void>> getCurrentUserRequests() throws Exception {
		return requestTrackerService.getCurrentUserRequests();
	}

	/**
	 * Endpoint to get a user vacation request by id.
	 * 
	 * @param id of the request
	 * @return {@link UserRequest}
	 */
	@GetMapping("/{id}/vacation")
	public UserRequest<Vacation> getUserVacationRequestById(@PathVariable int id) throws Exception {
		return requestTrackerService.getUserVacationRequestById(id);
	}

	/**
	 * Endpoint to get a user store request by id.
	 * 
	 * @param id of the request
	 * @return {@link UserRequest}
	 */
	@GetMapping("/{id}/store")
	public UserRequest<Store> getUserStoreMoveRequestById(@PathVariable int id) throws Exception {
		return requestTrackerService.getUserStoreRequestById(id);
	}
}
