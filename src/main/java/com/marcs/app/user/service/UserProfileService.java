package com.marcs.app.user.service;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import com.google.common.collect.Sets;
import com.marcs.app.auth.client.AuthenticationClient;
import com.marcs.app.email.client.EmailClient;
import com.marcs.app.notifications.client.NotificationClient;
import com.marcs.app.user.client.domain.Application;
import com.marcs.app.user.client.domain.PasswordUpdate;
import com.marcs.app.user.client.domain.User;
import com.marcs.app.user.client.domain.UserStatus;
import com.marcs.app.user.client.domain.request.UserGetRequest;
import com.marcs.app.user.dao.UserProfileDao;
import com.marcs.common.enums.AccountStatus;
import com.marcs.common.exceptions.BaseException;
import com.marcs.common.exceptions.InsufficientPermissionsException;
import com.marcs.jwt.utility.JwtHolder;
import com.marcs.service.util.PasswordUtil;

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
	private UserCredentialsService userCredentialsService;

	@Autowired
	private UserStatusService userStatusService;

	@Autowired
	private EmailClient emailClient;

	@Autowired
	private JwtHolder jwtHolder;

	@Autowired
	private AuthenticationClient authClient;

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
	 * Get the regional of the passed in store ID
	 * 
	 * @return The regional of that store
	 * @throws Exception
	 */
	public User getRegionalOfStoreById(String storeId) throws Exception {
		return dao.getRegionalOfStoreById(storeId);
	}

	/**
	 * Get the manager of the passed in store ID.
	 * 
	 * @return The manager of that store
	 * @throws Exception
	 */
	public User getManagerOfStoreById(String storeId) throws Exception {
		return dao.getManagerOfStoreById(storeId);
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

		userCredentialsService.insertUserPassword(newUser.getId(),
				PasswordUtil.hashPasswordWithSalt(user.getPassword()));
		userStatusService.insertUserStatus(new UserStatus(newUser.getId(), accountStatus, user.isAppAccess()));
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

		userCredentialsService.insertUserPassword(newUser.getId(),
				PasswordUtil.hashPasswordWithSalt(user.getPassword()));
		userStatusService.insertUserStatus(new UserStatus(newUser.getId(), AccountStatus.APPROVED, user.isAppAccess()));

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
		if (id != updatingUser.getId() && jwtHolder.getWebRole().id() <= updatingUser.getWebRole().id()) {
			throw new InsufficientPermissionsException(
					String.format("Your role of '%s' can not update a user of role '%s'", jwtHolder.getWebRole(),
							updatingUser.getWebRole()));
		}

		return updateUserProfile(id, user);
	}

	/**
	 * This will take in a {@link PasswordUpdate} object that will confirm that the
	 * current password matches the database password. If it does then it will
	 * update the password to the new password.
	 * 
	 * @param passUpdate Object the holds the current password and new user password
	 *                   to change it too.
	 * @return {@link User} object of the user that was updated.
	 * @throws Exception If the user can not be authenticated or the function was
	 *                   not able to hash the new password.
	 */
	public User updateUserPassword(PasswordUpdate passUpdate) throws Exception {
		authClient.authenticateUser(jwtHolder.getRequiredEmail(), passUpdate.getCurrentPassword()).getBody();
		return passwordUpdate(jwtHolder.getRequiredUserId(), passUpdate.getNewPassword());
	}

	/**
	 * Method that will take in an id and a PasswordUpdate object
	 * 
	 * @param passUpdate Object the holds the current password and new user password
	 *                   to change it too.
	 * @return {@link User} object of the user that was updated.
	 * @throws Exception If the user can not be authenticated or the function was
	 *                   not able to hash the new password.
	 */
	public User updateUserPasswordById(int userId, PasswordUpdate passUpdate) throws Exception {
		User updatingUser = getUserById(userId);
		if (userId != updatingUser.getId() && jwtHolder.getWebRole().id() <= updatingUser.getWebRole().id()) {
			throw new InsufficientPermissionsException(
					String.format("Your role of '%s' can not update a user of role '%s'", jwtHolder.getWebRole(),
							updatingUser.getWebRole()));
		}
		return passwordUpdate(userId, passUpdate.getNewPassword());
	}

	/**
	 * This will get called when a user has forgotten their password. This will
	 * allow them to reset it.
	 * 
	 * @param passUpdate Object the holds the current password and new user password
	 *                   to change it too.
	 * @return {@link User} object of the user that was updated.
	 * @throws Exception If the user can not be authenticated or the function was
	 *                   not able to hash the new password.
	 */
	public User resetUserPassword(String pass) throws Exception {
		if (!jwtHolder.getRequiredResetPassword()) {
			throw new Exception("Invalid token for reset password!");
		}

		return passwordUpdate(jwtHolder.getRequiredUserId(), pass);
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

	/**
	 * Update the users credentials.
	 * 
	 * @param userId   Id of the user wanting to update their password.
	 * @param password THe password to update on the user's account.
	 * @return user associated to that id with the updated information
	 * @throws Exception
	 */
	private User passwordUpdate(int userId, String password) throws Exception {
		try {
			if (password != null && password.trim() != "") {
				return userCredentialsService.updateUserPassword(userId, PasswordUtil.hashPasswordWithSalt(password));
			} else {
				return getCurrentUser();
			}
		} catch (NoSuchAlgorithmException e) {
			throw new BaseException("Could not hash password!");
		}
	}
}
