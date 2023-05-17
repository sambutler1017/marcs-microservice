/**
 * Copyright of Marcs App. All rights reserved.
 */
package com.marcs.app.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marcs.app.notifications.client.NotificationClient;
import com.marcs.app.store.client.StoreClient;
import com.marcs.app.user.client.UserCredentialsClient;
import com.marcs.app.user.client.UserStatusClient;
import com.marcs.app.user.client.domain.User;
import com.marcs.app.user.client.domain.UserStatus;
import com.marcs.app.user.dao.UserProfileDao;
import com.marcs.common.enums.AccountStatus;
import com.marcs.common.enums.WebRole;
import com.marcs.common.exceptions.InsufficientPermissionsException;
import com.marcs.jwt.utility.JwtHolder;

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
	private StoreClient storeClient;

	@Autowired
	private UserProfileService userProfileService;

	/**
	 * Creates a new user for the given user object.
	 * 
	 * @param user The user to create.
	 * @return {@link User} object of the users data.
	 */
	public User registerUser(User user, AccountStatus accountStatus) throws Exception {
		User newUser = dao.insertUser(user);

		userCredentialsClient.insertUserPassword(newUser.getId(), user.getPassword());
		userStatusClient.insertUserStatus(new UserStatus(newUser.getId(), accountStatus, false));
		notificationClient.sendNotification(newUser);
		return userProfileService.getUserById(newUser.getId());
	}

	/**
	 * Add's a new user. This is an account created by someone other the user
	 * accessing the account.
	 * 
	 * @param user The user to create.
	 * @return {@link User} object of the users data.
	 */
	public User addNewUser(User user) {
		user.setPassword("marcs123!");
		user.setAppAccess(true);

		User newUser = dao.insertUser(user);
		userCredentialsClient.insertUserPassword(newUser.getId(), user.getPassword());
		userStatusClient.insertUserStatus(new UserStatus(newUser.getId(), AccountStatus.APPROVED, user.isAppAccess()));

		if(WebRole.STORE_MANAGER.equals(newUser.getWebRole())) {
			processStoreManagerChange(newUser.getId(), newUser.getStoreId());
		}

		return userProfileService.getUserById(newUser.getId());
	}

	/**
	 * Update the user profile for the given user object.
	 * 
	 * @param user what information on the user needs to be updated.
	 * @return user associated to that id with the updated information.
	 */
	public User updateUserProfile(User user) {
		return updateUserProfile(jwtHolder.getUserId(), user);
	}

	/**
	 * Updates a user for the given id.
	 * 
	 * @param id               The id of the user
	 * @param userInfoToUpdate The info to update the user with
	 * @return user associated to that id with the updated information.
	 */
	public User updateUserProfileById(int id, User userInfoToUpdate) {
		User currentUser = userProfileService.getUserById(id);
		if(id != currentUser.getId() && jwtHolder.getWebRole().getRank() <= currentUser.getWebRole().getRank()) {
			throw new InsufficientPermissionsException(String
					.format("Your role of '%s' can not update a user of role '%s'", jwtHolder.getWebRole(),
							currentUser.getWebRole()));
		}

		User updatedUser = updateUserProfile(id, userInfoToUpdate);
		processRoleChange(currentUser, userInfoToUpdate);
		return updatedUser;
	}

	/**
	 * Method that will update the user's last login time to current date and time;
	 * 
	 * @param userId The user Id to be updated.
	 * @return The user object with the updated information.
	 */
	public User updateUserLastLoginToNow(int userId) {
		dao.updateUserLastLoginToNow(userId);
		return userProfileService.getUserById(userId);
	}

	/**
	 * Will patch a user for the given id.
	 * 
	 * @param id   of the user
	 * @param user The user info to be updated
	 * @return user associated to that id with the updated information
	 */
	public User patchUserProfileById(int id, User user) {
		dao.patchUserProfileById(id, user);
		return userProfileService.getUserById(id);
	}

	/**
	 * Delete the user for the given id.
	 * 
	 * @param id The id of the user being deleted.
	 */
	public void deleteUser(int id) {
		userProfileService.getUserById(id);
		dao.deleteUser(id);
	}

	/**
	 * Update the user for the given user object.
	 * 
	 * @param user what information on the user needs to be updated.
	 * @return user associated to that id with the updated information.
	 */
	private User updateUserProfile(int userId, User user) {
		dao.updateUserProfile(userId, user);
		return userProfileService.getUserById(userId);
	}

	/**
	 * Checks the role change of the user. If the role has changed it will check to
	 * see if any other steps need taken to confirm the role over change of the
	 * user.
	 * 
	 * @param currentInfo  The current user info
	 * @param updatingInfo The info to update on the user.
	 */
	private void processRoleChange(User currentInfo, User updatingInfo) {
		if(updatingInfo.getWebRole() != null && !currentInfo.getWebRole().equals(updatingInfo.getWebRole())) {
			switch(updatingInfo.getWebRole()) {
				case STORE_MANAGER:
					processStoreManagerChange(currentInfo.getId(), updatingInfo.getStoreId());
					break;
				case REGIONAL_MANAGER:
					processRegionalManagerChange(currentInfo.getId());
					break;
				default:
					processDefaultChange(currentInfo.getId());
					break;
			}
		}
	}

	/**
	 * Hepler method to process a role change to a
	 * {@link WebRole#CORPORATE_USER},{@link WebRole#EMPLOYEE},
	 * {@link WebRole#CUSTOMER_SERVICE_MANAGER}, {@link WebRole#ASSISTANT_MANAGER}
	 * {@link WebRole#SITE_ADMIN} or {@link WebRole#ADMIN}
	 * 
	 * @param userId The user id to process
	 */
	private void processDefaultChange(int userId) {
		storeClient.clearStoreManager(userId);
		storeClient.clearRegionalManager(userId);
	}

	/**
	 * Hepler method to process a role change to a {@link WebRole#STORE_MANAGER}
	 * 
	 * @param userId  The user id to process
	 * @param storeId The store id to set the manager for.
	 */
	private void processStoreManagerChange(int userId, String storeId) {
		storeClient.clearRegionalManager(userId);
		storeClient.updateStoreManagerOfStore(userId, storeId);
	}

	/**
	 * Hepler method to process a role change to a {@link WebRole#REGIONAL_MANAGER}
	 * or {@link WebRole#DISTRICT_MANAGER}
	 * 
	 * @param userId The user id to process
	 */
	private void processRegionalManagerChange(int userId) {
		storeClient.clearStoreManager(userId);
	}
}
