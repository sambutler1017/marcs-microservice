/**
 * Copyright of Marcs App. All rights reserved.
 */
package com.marcs.app.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Sets;
import com.marcs.app.email.client.EmailClient;
import com.marcs.app.user.client.domain.Application;
import com.marcs.app.user.client.domain.User;
import com.marcs.app.user.client.domain.request.UserGetRequest;
import com.marcs.app.user.dao.UserProfileDao;
import com.marcs.common.exceptions.BaseException;
import com.marcs.common.page.Page;
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

	@Autowired
	private EmailClient emailClient;

	/**
	 * Get users based on given request filter
	 * 
	 * @param request of the user
	 * @return User profile object {@link User}
	 */
	public Page<User> getUsers(UserGetRequest request) {
		return dao.getUsers(request);
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
	 * Service to get a users profile given the user id
	 * 
	 * @param id of the user
	 * @return User profile object {@link User}
	 */
	public User getUserById(int id) {
		UserGetRequest request = new UserGetRequest();
		request.setId(Sets.newHashSet(id));
		return getUsers(request).getList().get(0);
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
	 * This gets called when a user forgets their password. This will check to see
	 * if the passed in email exists as a user, if it does then the user will get an
	 * email to reset their passowrd.
	 * 
	 * @return user associated to that id with the updated information
	 */
	public User forgotPassword(String email) throws Exception {
		UserGetRequest request = new UserGetRequest();
		request.setEmail(Sets.newHashSet(email));
		List<User> users = getUsers(request).getList();

		if(users.size() == 0) {
			throw new BaseException(String.format("User not found for email '%s'", email));
		}

		emailClient.forgotPassword(email);
		return users.get(0);
	}
}
