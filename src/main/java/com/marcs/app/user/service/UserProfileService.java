/**
 * Copyright of Marcs App. All rights reserved.
 */
package com.marcs.app.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Sets;
import com.marcs.app.user.client.domain.Application;
import com.marcs.app.user.client.domain.User;
import com.marcs.app.user.client.domain.request.UserGetRequest;
import com.marcs.app.user.dao.UserProfileDao;
import com.marcs.common.page.Page;
import com.marcs.exceptions.type.UserNotFoundException;
import com.marcs.jwt.utility.JwtHolder;

/**
 * User Service class that handles all service calls to the dao
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Component
public class UserProfileService {

	@Autowired
	private UserProfileDao dao;

	@Autowired
	private JwtHolder jwtHolder;

	/**
	 * Get users based on given request filter
	 * 
	 * @param request of the user
	 * @return User profile object {@link User}
	 */
	public Page<User> getUsers(UserGetRequest request) {
		return dao.getUsers(userAccessRestrictions(request));
	}

	/**
	 * Service to get a users profile given the user id
	 * 
	 * @param id of the user
	 * @return User profile object {@link User}
	 */
	public User getUserById(int id) {
		return dao.getUserById(id)
				.orElseThrow(() -> new UserNotFoundException(String.format("User not found for id: '%d'", id)));
	}

	/**
	 * Get the current user from the jwt token.
	 * 
	 * @return User profile object {@link User}
	 */
	public User getCurrentUser() {
		return getUserById(jwtHolder.getUserId());
	}

	/**
	 * Service to get a list of Applications for the logged in user
	 * 
	 * @return List of Application objects {@link Application}
	 */
	public List<Application> getUserApps() {
		return getUserAppsById(jwtHolder.getUserId());
	}

	/**
	 * End point to a get a list of users apps that they have access too
	 * 
	 * @return List of Application objects {@link Application}
	 */
	public List<Application> getUserAppsById(int id) {
		return dao.getUserApps(id);
	}

	/**
	 * This will check to see if the email exists. If it does then it will return
	 * true, otherwise false.
	 * 
	 * @param email The email to check
	 * @return {@link Boolean} to see if the email exists
	 */
	public boolean doesEmailExist(String email) {
		UserGetRequest request = new UserGetRequest();
		request.setEmail(Sets.newHashSet(email));
		List<User> users = getUsers(request).getList();

		return users.size() > 0;
	}

	/**
	 * Will determine what users, the user making the request, is able to see.
	 * 
	 * @param r The passed in user get request.
	 * @return Updated user get request.
	 */
	private UserGetRequest userAccessRestrictions(UserGetRequest r) {
		if(!jwtHolder.isTokenAvaiable()) {
			return r;
		}

		if(jwtHolder.getWebRole().isAllAccessUser()) {
			r.setExcludedUserIds(Sets.newHashSet(jwtHolder.getUserId()));
		}
		else if(jwtHolder.getWebRole().isRegionalManager()) {
			r.setExcludedUserIds(Sets.newHashSet(jwtHolder.getUserId()));
			r.setRegionalManagerId(Sets.newHashSet(jwtHolder.getUserId()));
		}
		else if(jwtHolder.getWebRole().isManager()) {
			r.setExcludedUserIds(Sets.newHashSet(jwtHolder.getUserId()));
			r.setStoreId(Sets.newHashSet(jwtHolder.getUser().getStoreId()));
		}
		else { // Employee User
			r.setId(Sets.newHashSet(jwtHolder.getUserId()));
		}

		return r;
	}
}
