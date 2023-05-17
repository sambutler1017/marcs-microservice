/**
 * Copyright of Marcs App. All rights reserved.
 */
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
	 */
	public List<User> getUsers() {
		return controller.getUsers(new UserGetRequest()).getList();
	}

	/**
	 * Get users based on given request filter.
	 * 
	 * @param request of the user
	 * @return User profile object {@link User}
	 */
	public List<User> getUsers(UserGetRequest request) {
		return controller.getUsers(request).getList();
	}

	/**
	 * Gets the current logged in user information.
	 * 
	 * @return The user currently logged in.
	 */
	public User getCurrentUser() {
		return controller.getCurrentUser();
	}

	/**
	 * Client method to get the user given a user id
	 * 
	 * @param id of the user
	 * @return User profile object
	 */
	public User getUserById(int id) {
		return controller.getUserById(id);
	}

	/**
	 * Client method to a get a list of users apps that they have access too
	 * 
	 * @return List of Application objects {@link Application}
	 */
	public List<Application> getUserApps() {
		return controller.getUserApps();
	}

	/**
	 * Client method to a get a list of users apps for a given id
	 * 
	 * @return List of Application objects {@link Application}
	 * @since May 13, 2020
	 */
	public List<Application> getUserAppsById(int id) {
		return controller.getUserAppsById(id);
	}

	/**
	 * Creates a new user.
	 * 
	 * @param user The user to be created.
	 * @return The user that would be created.
	 */
	public User registerUser(User user) throws Exception {
		return controller.registerUser(user);
	}

	/**
	 * Allows a user to create a new user account.
	 * 
	 * @param user The user to be created.
	 * @return The user that would be created.
	 */
	public User addNewUser(User user) {
		return controller.addNewUser(user);
	}

	/**
	 * Update the user's information such as email, first name, last name, and
	 * password
	 * 
	 * @param user what information on the user needs to be updated.
	 * @return user associated to that id with the updated information
	 */
	public User updateUserProfile(User user) {
		return controller.updateUserProfile(user);
	}

	/**
	 * Updates a user for the given id.
	 * 
	 * @param id of the user
	 * @return user associated to that id with the updated information
	 */
	public User updateUserProfileById(int id, User user) {
		return controller.updateUserProfileById(id, user);
	}

	/**
	 * Method that will update the user's last login time to current date and time;
	 * 
	 * @param userId The user Id to be updated.
	 * @return The user object with the updated information.
	 */
	public User updateUserLastLoginToNow(int id) {
		return controller.updateUserLastLoginToNow(id);
	}

	/**
	 * Will patch a user for the given id.
	 * 
	 * @param id   of the user
	 * @param user The user info to be updated
	 * @return user associated to that id with the updated information
	 */
	public User patchUserProfileById(int id, User user) {
		return controller.patchUserProfileById(id, user);
	}

	/**
	 * Delete the user for the given id.
	 * 
	 * @param id The id of the user being deleted
	 */
	public void deleteUser(int id) {
		controller.deleteUser(id);
	}
}
