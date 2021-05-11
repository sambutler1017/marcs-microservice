package com.marcs.app.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marcs.app.user.client.domain.UserCredentials;
import com.marcs.app.user.client.domain.UserProfile;
import com.marcs.app.user.client.domain.UserSecurity;
import com.marcs.app.user.client.domain.request.UserCredentialsGetRequest;
import com.marcs.app.user.dao.UserDAO;

/**
 * User Service class that handles all service calls to the dao
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Component
public class UserService {

	@Autowired
	private UserDAO userDao;

	/**
	 * Service to get a users profile given the user id
	 * 
	 * @param id of the user
	 * @return User profile object {@link UserProfile}
	 */
	public UserProfile getUserById(int id) {
		return userDao.getUserById(id);
	}

	/**
	 * Service to get a users login credentials
	 * 
	 * @param request to filter on {@link UserCredentialsGetRequest}
	 * @return User Credentials object {@link UserCredentials}
	 */
	public UserCredentials getUserCredentials(UserCredentialsGetRequest request) {
		return userDao.getUserCredentials(request);
	}

	/**
	 * Service to get a users security info
	 * 
	 * @param id of the user
	 * @return User Security object {@link UserSecurity}
	 */
	public UserSecurity getUserSecurity(int id) {
		return userDao.getUserSecurity(id);
	}
}
