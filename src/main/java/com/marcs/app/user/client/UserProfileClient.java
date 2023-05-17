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
import com.marcs.app.user.service.ManageUserProfileService;
import com.marcs.app.user.service.UserProfileService;
import com.marcs.common.enums.AccountStatus;

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
	private UserProfileService userProfileService;

	@Autowired
	private ManageUserProfileService manageUserProfileService;

	/**
	 * Gets a list of users based of the request filter
	 * 
	 * @param request to filter on
	 * @return list of user objects
	 */
	public List<User> getUsers(UserGetRequest request) {
		return userProfileService.getUsers(request).getList();
	}

	/**
	 * Gets the current logged in user information.
	 * 
	 * @return The user currently logged in.
	 */
	public User getCurrentUser() {
		return userProfileService.getCurrentUser();
	}

	/**
	 * Get user object for the given Id
	 * 
	 * @param id of the user
	 * @return user associated to that id
	 */
	public User getUserById(int id) {
		return userProfileService.getUserById(id);
	}

	/**
	 * End point to a get a list of users apps that they have access too
	 * 
	 * @return List of Application objects {@link Application}
	 */
	public List<Application> getUserApps() {
		return userProfileService.getUserApps();
	}

	/**
	 * End point to a get a list of users apps that they have access too
	 * 
	 * @return List of Application objects {@link Application}
	 */
	public List<Application> getUserAppsById(int id) {
		return userProfileService.getUserAppsById(id);
	}

	/**
	 * This will check to see if the email exists. If it does then it will return
	 * true, otherwise false.
	 * 
	 * @param email The email to check
	 * @return {@link Boolean} to see if the email exists
	 */
	public boolean doesEmailExist(String email) {
		return userProfileService.doesEmailExist(email);
	}

	/**
	 * Creates a new user for the given user object.
	 * 
	 * @param user The user to create.
	 * @return {@link User} object of the users data.
	 */
	public User registerUser(User user) throws Exception {
		return manageUserProfileService.registerUser(user, AccountStatus.PENDING);
	}

	/**
	 * Add's a new user. This is an account created by someone other the user
	 * accessing the account.
	 * 
	 * @param user The user to create.
	 * @return {@link User} object of the users data.
	 */
	public User addNewUser(User user) {
		return manageUserProfileService.addNewUser(user);
	}

	/**
	 * This gets called when a user forgets their password. This will check to see
	 * if the passed in email exists as a user, if it does then the user will get an
	 * email to reset their passowrd.
	 * 
	 * @return user associated to that id with the updated information
	 */
	public User forgotPassword(String email) throws Exception {
		return userProfileService.forgotPassword(email);
	}

	/**
	 * Update the user's information such as email, first name, last name, and
	 * password
	 * 
	 * @param user what information on the user needs to be updated.
	 * @return user associated to that id with the updated information
	 */
	public User updateUserProfile(User user) {
		return manageUserProfileService.updateUserProfile(user);
	}

	/**
	 * Updates a user for the given id.
	 * 
	 * @param id of the user
	 * @return user associated to that id with the updated information
	 */
	public User updateUserProfileById(int id, User user) {
		return manageUserProfileService.updateUserProfileById(id, user);
	}

	/**
	 * Method that will update the user's last login time to current date and time;
	 * 
	 * @param userId The user Id to be updated.
	 * @return The user object with the updated information.
	 */
	public User updateUserLastLoginToNow(int id) {
		return manageUserProfileService.updateUserLastLoginToNow(id);
	}

	/**
	 * Will patch a user for the given id.
	 * 
	 * @param id   of the user
	 * @param user The user info to be updated
	 * @return user associated to that id with the updated information
	 */
	public User patchUserProfileById(int id, User user) {
		return manageUserProfileService.patchUserProfileById(id, user);
	}

	/**
	 * Delete the user for the given id.
	 * 
	 * @param id The id of the user being deleted
	 */
	public void deleteUser(int id) {
		manageUserProfileService.deleteUser(id);
	}
}
