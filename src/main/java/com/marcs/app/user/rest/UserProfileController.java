package com.marcs.app.user.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import com.marcs.app.user.client.domain.Application;
import com.marcs.app.user.client.domain.User;
import com.marcs.app.user.client.domain.request.UserGetRequest;
import com.marcs.app.user.service.UserProfileService;
import com.marcs.common.enums.AccountStatus;

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

@RequestMapping("api/user-app/user-profile")
@RestController
public class UserProfileController {

	@Autowired
	private UserProfileService service;

	/**
	 * Gets a list of users based of the request filter
	 * 
	 * @param request to filter on
	 * @return list of user objects
	 * @throws Exception
	 */
	@GetMapping(produces = APPLICATION_JSON_VALUE)
	public List<User> getUsers(UserGetRequest request) throws Exception {
		return service.getUsers(request);
	}

	/**
	 * Gets the current logged in user information.
	 * 
	 * @return The user currently logged in.
	 * @throws Exception
	 */
	@GetMapping(path = "/current-user", produces = APPLICATION_JSON_VALUE)
	public User getCurrentUser() throws Exception {
		return service.getCurrentUser();
	}

	/**
	 * Get user object for the given Id
	 * 
	 * @param id of the user
	 * @return user associated to that id
	 * @throws Exception
	 */
	@GetMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
	public User getUserById(@PathVariable int id) throws Exception {
		return service.getUserById(id);
	}

	/**
	 * End point to a get a list of users apps that they have access too
	 * 
	 * @return List of Application objects {@link Application}
	 * @throws Exception
	 */
	@GetMapping(path = "/application-access", produces = APPLICATION_JSON_VALUE)
	public List<Application> getUserApps() throws Exception {
		return service.getUserApps();
	}

	/**
	 * End point to a get a list of users apps that they have access too
	 * 
	 * @return List of Application objects {@link Application}
	 * @throws Exception
	 */
	@GetMapping(path = "/{id}/application-access", produces = APPLICATION_JSON_VALUE)
	public List<Application> getUserAppsById(@PathVariable int id) throws Exception {
		return service.getUserAppsById(id);
	}

	/**
	 * This will check to see if the email exists. If it does then it will return
	 * true, otherwise false.
	 * 
	 * @param email The email to check
	 * @return {@link Boolean} to see if the email exists
	 * @throws Exception
	 */
	@GetMapping("/check-email")
	public boolean doesEmailExist(@RequestParam String email) throws Exception {
		return service.doesEmailExist(email);
	}

	/**
	 * Creates a new user for the given user object.
	 * 
	 * @param user The user to create.
	 * @return {@link User} object of the users data.
	 * @throws Exception
	 */
	@PostMapping(produces = APPLICATION_JSON_VALUE)
	public User createUser(@RequestBody User user) throws Exception {
		return service.createUser(user, AccountStatus.PENDING);
	}

	/**
	 * Add's a new user. This is an account created by someone other the user
	 * accessing the account.
	 * 
	 * @param user The user to create.
	 * @return {@link User} object of the users data.
	 * @throws Exception
	 */
	@PostMapping(path = "/add-user", produces = APPLICATION_JSON_VALUE)
	public User addNewUser(@RequestBody User user) throws Exception {
		return service.addNewUser(user);
	}

	/**
	 * This gets called when a user forgets their password. This will check to see
	 * if the passed in email exists as a user, if it does then the user will get an
	 * email to reset their passowrd.
	 * 
	 * @return user associated to that id with the updated information
	 * @throws Exception
	 */
	@PostMapping(path = "/forgot-password", produces = APPLICATION_JSON_VALUE)
	public User forgotPassword(@RequestBody String email) throws Exception {
		return service.forgotPassword(email);
	}

	/**
	 * Update the user's information such as email, first name, last name, and
	 * password
	 * 
	 * @param user what information on the user needs to be updated.
	 * @return user associated to that id with the updated information
	 * @throws Exception
	 */
	@PutMapping(produces = APPLICATION_JSON_VALUE)
	public User updateUserProfile(@RequestBody User user) throws Exception {
		return service.updateUserProfile(user);
	}

	/**
	 * Updates a user for the given id.
	 * 
	 * @param id of the user
	 * @return user associated to that id with the updated information
	 * @throws Exception
	 */
	@PutMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
	public User updateUserProfileById(@PathVariable int id, @RequestBody User user) throws Exception {
		return service.updateUserProfileById(id, user);
	}

	/**
	 * Delete the user for the given id.
	 * 
	 * @param id The id of the user being deleted
	 * @throws Exception
	 */
	@DeleteMapping("/{id}")
	public void deleteUser(@PathVariable int id) throws Exception {
		service.deleteUser(id);
	}
}