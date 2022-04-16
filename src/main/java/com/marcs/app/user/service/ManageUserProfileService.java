package com.marcs.app.user.service;

import com.marcs.app.notifications.client.NotificationClient;
import com.marcs.app.user.client.UserCredentialsClient;
import com.marcs.app.user.client.UserStatusClient;
import com.marcs.app.user.client.domain.User;
import com.marcs.app.user.client.domain.UserStatus;
import com.marcs.app.user.dao.UserProfileDao;
import com.marcs.common.enums.AccountStatus;
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
public class ManageUserProfileService {

	@Autowired
	private UserProfileDao dao;

	@Autowired
	private JwtHolder jwtHolder;

	@Autowired
	private UserCredentialsClient userCredentialsClient;

	@Autowired
	private UserStatusClient userStatusClient;

	@Autowired
	private NotificationClient notificationClient;

	@Autowired
	private UserProfileService userProfileService;

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
		userStatusClient.insertUserStatus(new UserStatus(newUser.getId(), accountStatus, false));
		notificationClient.createNotificationForUser(newUser);
		return userProfileService.getUserById(newUser.getId());
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
		userStatusClient.insertUserStatus(new UserStatus(newUser.getId(), AccountStatus.APPROVED, user.isAppAccess()));

		return userProfileService.getUserById(newUser.getId());
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
		User updatingUser = userProfileService.getUserById(id);
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
		userProfileService.getUserById(id);
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
