package com.marcs.app.user.service;

import java.util.List;

import com.google.common.collect.Sets;
import com.marcs.app.email.client.EmailClient;
import com.marcs.app.notifications.client.NotificationClient;
import com.marcs.app.user.client.UserCredentialsClient;
import com.marcs.app.user.client.UserStatusClient;
import com.marcs.app.user.client.domain.Application;
import com.marcs.app.user.client.domain.User;
import com.marcs.app.user.client.domain.UserStatus;
import com.marcs.app.user.client.domain.request.UserGetRequest;
import com.marcs.app.user.dao.UserProfileDao;
import com.marcs.common.enums.AccountStatus;
import com.marcs.common.exceptions.BaseException;
import com.marcs.common.exceptions.InsufficientPermissionsException;
import com.marcs.jwt.utility.JwtHolder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
	private UserCredentialsClient userCredentialsClient;

	@Autowired
	private UserStatusClient UserStatusClient;

	@Autowired
	private EmailClient emailClient;

	@Autowired
	private JwtHolder jwtHolder;

	@Autowired
	private NotificationClient notificationClient;

	/**
	 * Get users based on given request filter
	 * 
	 * @param request of the user
	 * @return User profile object {@link User}
	 * @throws Exception
	 */
	public List<User> getUsers(UserGetRequest request) throws Exception {
		return dao.getUsers(request);
	}

	/**
	 * Get the current user from the jwt token.
	 * 
	 * @return User profile object {@link User}
	 * @throws Exception
	 */
	public User getCurrentUser() throws Exception {
		return getUserById(jwtHolder.getRequiredUserId());
	}

	/**
	 * Service to get a users profile given the user id
	 * 
	 * @param id of the user
	 * @return User profile object {@link User}
	 * @throws Exception
	 */
	public User getUserById(int id) throws Exception {
		return dao.getUserById(id);
	}

	/**
	 * Service to get a list of Applications for the logged in user
	 * 
	 * @return List of Application objects {@link Application}
	 * @throws Exception
	 */
	public List<Application> getUserApps() throws Exception {
		return dao.getUserApps();
	}

	/**
	 * End point to a get a list of users apps that they have access too
	 * 
	 * @return List of Application objects {@link Application}
	 * @throws Exception
	 * @since May 13, 2020
	 */
	public List<Application> getUserAppsById(int id) throws Exception {
		return dao.getUserApps(id);
	}

	/**
	 * This will check to see if the email exists. If it does then it will return
	 * true, otherwise false.
	 * 
	 * @param email The email to check
	 * @return {@link Boolean} to see if the email exists
	 * @throws Exception
	 */
	public boolean doesEmailExist(String email) throws Exception {
		UserGetRequest request = new UserGetRequest();
		request.setEmail(Sets.newHashSet(email));
		List<User> users = getUsers(request);

		return users.size() > 0;
	}

	/**
	 * Creates a new user for the given user object.
	 * 
	 * @param user The user to create.
	 * @return {@link User} object of the users data.
	 * @throws Exception
	 */
	public User createUser(User user, AccountStatus accountStatus) throws Exception {
		User newUser = dao.insertUser(user);

		userCredentialsClient.insertUserPassword(newUser.getId(), user.getPassword());
		UserStatusClient.insertUserStatus(new UserStatus(newUser.getId(), accountStatus, false));
		notificationClient.createNotificationForUser(newUser);
		return getUserById(newUser.getId());
	}

	/**
	 * Add's a new user. This is an account created by someone other the user
	 * accessing the account.
	 * 
	 * @param user The user to create.
	 * @return {@link User} object of the users data.
	 * @throws Exception
	 */
	public User addNewUser(User user) throws Exception {
		user.setPassword("marcs123!");
		user.setAppAccess(true);

		User newUser = dao.insertUser(user);

		userCredentialsClient.insertUserPassword(newUser.getId(), user.getPassword());
		UserStatusClient.insertUserStatus(new UserStatus(newUser.getId(), AccountStatus.APPROVED, user.isAppAccess()));

		return getUserById(newUser.getId());
	}

	/**
	 * This gets called when a user forgets their password. This will check to see
	 * if the passed in email exists as a user, if it does then the user will get an
	 * email to reset their passowrd.
	 * 
	 * @return user associated to that id with the updated information
	 * @throws Exception
	 */
	public User forgotPassword(String email) throws Exception {
		UserGetRequest request = new UserGetRequest();
		request.setEmail(Sets.newHashSet(email));
		List<User> users = getUsers(request);

		if (users.size() == 0) {
			throw new BaseException(String.format("User not found for email '%s'", email));
		}

		emailClient.forgotPassword(email);
		return users.get(0);
	}

	/**
	 * Update the user profile for the given user object.
	 * 
	 * @param user what information on the user needs to be updated.
	 * @return user associated to that id with the updated information
	 * @throws Exception
	 */
	public User updateUserProfile(User user) throws Exception {
		return updateUserProfile(jwtHolder.getRequiredUserId(), user);
	}

	/**
	 * Updates a user for the given id.
	 * 
	 * @param id of the user
	 * @return user associated to that id with the updated information
	 * @throws Exception
	 */
	public User updateUserProfileById(int id, User user) throws Exception {
		User updatingUser = getUserById(id);
		if (id != updatingUser.getId() && jwtHolder.getWebRole().getRank() <= updatingUser.getWebRole().getRank()) {
			throw new InsufficientPermissionsException(
					String.format("Your role of '%s' can not update a user of role '%s'", jwtHolder.getWebRole(),
							updatingUser.getWebRole()));
		}

		return updateUserProfile(id, user);
	}

	/**
	 * Delete the user for the given id.
	 * 
	 * @param id The id of the user being deleted
	 * @throws Exception
	 */
	public void deleteUser(int id) throws Exception {
		getUserById(id);
		dao.deleteUser(id);
	}

	/**
	 * Update the user for the given user object.
	 * 
	 * @param user what information on the user needs to be updated.
	 * @return user associated to that id with the updated information
	 * @throws Exception
	 */
	private User updateUserProfile(int userId, User user) throws Exception {
		return dao.updateUserProfile(userId, user);
	}
}
