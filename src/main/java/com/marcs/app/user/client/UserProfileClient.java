package com.marcs.app.user.client;

import java.util.List;

import com.marcs.app.user.client.domain.Application;
import com.marcs.app.user.client.domain.User;
import com.marcs.app.user.client.domain.request.UserGetRequest;
import com.marcs.app.user.rest.UserProfileController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This class exposes the user endpoint's to other app's to pull data across the
 * platform.
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Component
public class UserProfileClient {

	@Autowired
	private UserProfileController userController;

	/**
	 * Get users based on given request filter
	 * 
	 * @param request of the user
	 * @return User profile object {@link User}
	 * @throws Exception
	 */
	public List<User> getUsers(UserGetRequest request) throws Exception {
		return userController.getUsers(request);
	}

	/**
	 * Gets the current logged in user information.
	 * 
	 * @return The user currently logged in.
	 * @throws Exception
	 */
	public User getCurrentUser() throws Exception {
		return userController.getCurrentUser();
	}

	/**
	 * Client method to get the user given a user id
	 * 
	 * @param id of the user
	 * @return User profile object
	 * @throws Exception
	 */
	public User getUserById(int id) throws Exception {
		return userController.getUserById(id);
	}

	/**
	 * Client method to a get a list of users apps that they have access too
	 * 
	 * @return List of Application objects {@link Application}
	 * @throws Exception
	 */
	public List<Application> getUserApps() throws Exception {
		return userController.getUserApps();
	}

	/**
	 * Client method to a get a list of users apps for a given id
	 * 
	 * @return List of Application objects {@link Application}
	 * @throws Exception
	 * @since May 13, 2020
	 */
	public List<Application> getUserAppsById(int id) throws Exception {
		return userController.getUserAppsById(id);
	}

	/**
	 * Creates a new user.
	 * 
	 * @param user The user to be created.
	 * @return The user that would be created.
	 * @throws Exception
	 */
	public User createUser(User user) throws Exception {
		return userController.createUser(user);
	}

	/**
	 * Allows a user to create a new user account.
	 * 
	 * @param user The user to be created.
	 * @return The user that would be created.
	 * @throws Exception
	 */
	public User addNewUser(User user) throws Exception {
		return userController.addNewUser(user);
	}

	/**
	 * Delete the user for the given id.
	 * 
	 * @param id The id of the user being deleted
	 */
	public void deleteUser(int id) throws Exception {
		userController.deleteUser(id);
	}
}
