package com.marcs.app.user.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.marcs.annotations.interfaces.Client;
import com.marcs.app.user.client.domain.Application;
import com.marcs.app.user.client.domain.User;
import com.marcs.app.user.client.domain.request.UserGetRequest;
import com.marcs.app.user.rest.UserProfileController;

/**
 * This class exposes the user endpoint's to other app's to pull data across the
 * platform.
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Client
public class UserProfileClient {

	@Autowired
	private UserProfileController controller;

	/**
	 * Get users with no filters.
	 * 
	 * @param request of the user
	 * @return User profile object {@link User}
	 * @throws Exception
	 */
	public List<User> getUsers() throws Exception {
		return controller.getUsers(new UserGetRequest()).getList();
	}

	/**
	 * Get users based on given request filter.
	 * 
	 * @param request of the user
	 * @return User profile object {@link User}
	 * @throws Exception
	 */
	public List<User> getUsers(UserGetRequest request) throws Exception {
		return controller.getUsers(request).getList();
	}

	/**
	 * Gets the current logged in user information.
	 * 
	 * @return The user currently logged in.
	 * @throws Exception
	 */
	public User getCurrentUser() throws Exception {
		return controller.getCurrentUser();
	}

	/**
	 * Client method to get the user given a user id
	 * 
	 * @param id of the user
	 * @return User profile object
	 * @throws Exception
	 */
	public User getUserById(int id) throws Exception {
		return controller.getUserById(id);
	}

	/**
	 * Client method to a get a list of users apps that they have access too
	 * 
	 * @return List of Application objects {@link Application}
	 * @throws Exception
	 */
	public List<Application> getUserApps() throws Exception {
		return controller.getUserApps();
	}

	/**
	 * Client method to a get a list of users apps for a given id
	 * 
	 * @return List of Application objects {@link Application}
	 * @throws Exception
	 * @since May 13, 2020
	 */
	public List<Application> getUserAppsById(int id) throws Exception {
		return controller.getUserAppsById(id);
	}

	/**
	 * Creates a new user.
	 * 
	 * @param user The user to be created.
	 * @return The user that would be created.
	 * @throws Exception
	 */
	public User createUser(User user) throws Exception {
		return controller.createUser(user);
	}

	/**
	 * Allows a user to create a new user account.
	 * 
	 * @param user The user to be created.
	 * @return The user that would be created.
	 * @throws Exception
	 */
	public User addNewUser(User user) throws Exception {
		return controller.addNewUser(user);
	}

	/**
	 * Update the user's information such as email, first name, last name, and
	 * password
	 * 
	 * @param user what information on the user needs to be updated.
	 * @return user associated to that id with the updated information
	 * @throws Exception
	 */
	public User updateUserProfile(User user) throws Exception {
		return controller.updateUserProfile(user);
	}

	/**
	 * Updates a user for the given id.
	 * 
	 * @param id of the user
	 * @return user associated to that id with the updated information
	 * @throws Exception
	 */
	public User updateUserProfileById(int id, User user) throws Exception {
		return controller.updateUserProfileById(id, user);
	}

	/**
	 * Method that will update the user's last login time to current date and time;
	 * 
	 * @param userId The user Id to be updated.
	 * @return The user object with the updated information.
	 * @throws Exception
	 */
	public User updateUserLastLoginToNow(int id) throws Exception {
		return controller.updateUserLastLoginToNow(id);
	}

	/**
	 * Delete the user for the given id.
	 * 
	 * @param id The id of the user being deleted
	 */
	public void deleteUser(int id) throws Exception {
		controller.deleteUser(id);
	}
}
