/**
 * Copyright of Marcs App. All rights reserved.
 */
package com.marcs.app.user.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.marcs.app.user.client.domain.Application;
import com.marcs.app.user.client.domain.User;
import com.marcs.app.user.client.domain.request.UserGetRequest;
import com.marcs.app.user.service.ManageUserProfileService;
import com.marcs.app.user.service.UserProfileService;
import com.marcs.common.enums.AccountStatus;
import com.marcs.common.page.Page;

@RequestMapping("api/user-app/user-profile")
@RestController
public class UserProfileController {

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
	@GetMapping(produces = APPLICATION_JSON_VALUE)
	public Page<User> getUsers(UserGetRequest request) {
		return userProfileService.getUsers(request);
	}

	/**
	 * Gets the current logged in user information.
	 * 
	 * @return The user currently logged in.
	 */
	@GetMapping(path = "/current-user", produces = APPLICATION_JSON_VALUE)
	public User getCurrentUser() {
		return userProfileService.getCurrentUser();
	}

	/**
	 * Get user object for the given Id
	 * 
	 * @param id of the user
	 * @return user associated to that id
	 */
	@GetMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
	public User getUserById(@PathVariable int id) {
		return userProfileService.getUserById(id);
	}

	/**
	 * End point to a get a list of users apps that they have access too
	 * 
	 * @return List of Application objects {@link Application}
	 */
	@GetMapping(path = "/application-access", produces = APPLICATION_JSON_VALUE)
	public List<Application> getUserApps() {
		return userProfileService.getUserApps();
	}

	/**
	 * End point to a get a list of users apps that they have access too
	 * 
	 * @return List of Application objects {@link Application}
	 */
	@GetMapping(path = "/{id}/application-access", produces = APPLICATION_JSON_VALUE)
	public List<Application> getUserAppsById(@PathVariable int id) {
		return userProfileService.getUserAppsById(id);
	}

	/**
	 * This will check to see if the email exists. If it does then it will return
	 * true, otherwise false.
	 * 
	 * @param email The email to check
	 * @return {@link Boolean} to see if the email exists
	 */
	@GetMapping("/check-email")
	public boolean doesEmailExist(@RequestParam String email) {
		return userProfileService.doesEmailExist(email);
	}

	/**
	 * Creates a new user for the given user object.
	 * 
	 * @param user The user to create.
	 * @return {@link User} object of the users data.
	 */
	@PostMapping(produces = APPLICATION_JSON_VALUE)
	public User createUser(@RequestBody User user) throws Exception {
		return manageUserProfileService.createUser(user, AccountStatus.PENDING);
	}

	/**
	 * Add's a new user. This is an account created by someone other the user
	 * accessing the account.
	 * 
	 * @param user The user to create.
	 * @return {@link User} object of the users data.
	 */
	@PostMapping(path = "/add-user", produces = APPLICATION_JSON_VALUE)
	public User addNewUser(@RequestBody User user) {
		return manageUserProfileService.addNewUser(user);
	}

	/**
	 * This gets called when a user forgets their password. This will check to see
	 * if the passed in email exists as a user, if it does then the user will get an
	 * email to reset their passowrd.
	 * 
	 * @return user associated to that id with the updated information
	 */
	@PostMapping(path = "/forgot-password", produces = APPLICATION_JSON_VALUE)
	public User forgotPassword(@RequestBody String email) throws Exception {
		return userProfileService.forgotPassword(email);
	}

	/**
	 * Update the user's information such as email, first name, last name, and
	 * password
	 * 
	 * @param user what information on the user needs to be updated.
	 * @return user associated to that id with the updated information
	 */
	@PutMapping(produces = APPLICATION_JSON_VALUE)
	public User updateUserProfile(@RequestBody User user) {
		return manageUserProfileService.updateUserProfile(user);
	}

	/**
	 * Updates a user for the given id.
	 * 
	 * @param id of the user
	 * @return user associated to that id with the updated information
	 */
	@PutMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
	public User updateUserProfileById(@PathVariable int id, @RequestBody User user) {
		return manageUserProfileService.updateUserProfileById(id, user);
	}

	/**
	 * Method that will update the user's last login time to current date and time;
	 * 
	 * @param userId The user Id to be updated.
	 * @return The user object with the updated information.
	 */
	@PutMapping(path = "/{id}/last-login", produces = APPLICATION_JSON_VALUE)
	public User updateUserLastLoginToNow(@PathVariable int id) {
		return manageUserProfileService.updateUserLastLoginToNow(id);
	}

	/**
	 * Delete the user for the given id.
	 * 
	 * @param id The id of the user being deleted
	 */
	@DeleteMapping("/{id}")
	public void deleteUser(@PathVariable int id) {
		manageUserProfileService.deleteUser(id);
	}
}