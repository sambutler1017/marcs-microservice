package com.marcs.app.user.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marcs.app.user.client.domain.UserCredentials;
import com.marcs.app.user.client.domain.UserProfile;
import com.marcs.app.user.client.domain.UserSecurity;
import com.marcs.app.user.client.domain.request.UserCredentialsGetRequest;
import com.marcs.app.user.rest.UserController;

/**
 * This class exposes the user endpoint's to other app's to pull data across the
 * platform.
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Component
public class UserClient {

	@Autowired
	private UserController userController;

	/**
	 * Client method to get the user given a user id
	 * 
	 * @param id of the user
	 * @return User profile object
	 * @throws Exception
	 */
	public UserProfile getUserById(int id) throws Exception {
		return userController.getUserById(id);
	}

	/**
	 * Client method to get the user credentials given a request
	 * 
	 * @param request of what to filter {@link USerCredentialsGetRequest}
	 * @return User Credentials object
	 * @throws Exception
	 */
	public UserCredentials getUserCredentials(UserCredentialsGetRequest request) throws Exception {
		return userController.getUserCredentials(request);
	}

	/**
	 * Client method to get the users security data given the user id
	 * 
	 * @param id of the user
	 * @return User Security object
	 * @throws Exception
	 */
	public UserSecurity getUserSecurity(int id) throws Exception {
		return userController.getUserSecurity(id);
	}

}
