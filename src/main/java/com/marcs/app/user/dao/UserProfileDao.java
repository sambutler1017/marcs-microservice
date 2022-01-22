package com.marcs.app.user.dao;

import static com.marcs.app.user.mapper.ApplicationMapper.APPLICATION_MAPPER;
import static com.marcs.app.user.mapper.UserProfileMapper.USER_MAPPER;

import java.time.LocalDate;
import java.util.List;

import com.marcs.app.user.client.domain.Application;
import com.marcs.app.user.client.domain.User;
import com.marcs.app.user.client.domain.request.UserGetRequest;
import com.marcs.common.abstracts.AbstractSqlDao;
import com.marcs.common.exceptions.UserNotFoundException;
import com.marcs.jwt.utility.JwtHolder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Class that handles all the dao calls to the database for users
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Repository
public class UserProfileDao extends AbstractSqlDao {

	@Autowired
	private JwtHolder jwtHolder;

	/**
	 * Get users based on given request filter
	 * 
	 * @param request of the user
	 * @return User profile object {@link User}
	 * @throws Exception
	 */
	public List<User> getUsers(UserGetRequest request) throws Exception {
		return sqlClient.getPage(getSql("getUsers"),
				params("id", request.getId()).addValue("email", request.getEmail())
						.addValue("webRole", request.getWebRole()).addValue("storeId", request.getStoreId())
						.addValue("regionalId", request.getRegionalId()).addValue("firstName", request.getFirstName())
						.addValue("lastName", request.getLastName()).addValue("storeName", request.getStoreName())
						.addValue("notificationsEnabled", request.getNotificationsEnabled())
						.addValue("accountStatus", request.getAccountStatus())
						.addValue("excludedUserIds", request.getExcludedUserIds()),
				USER_MAPPER);
	}

	/**
	 * This method returns a user profile object containing profile type information
	 * about the user
	 * 
	 * @param id of the user
	 * @return User profile object {@link UserProfile}
	 * @throws Exception
	 */
	public User getUserById(int id) throws Exception {
		try {
			return sqlClient.getTemplate(getSql("getUserById"), params("id", id), USER_MAPPER);
		} catch (Exception e) {
			throw new UserNotFoundException(String.format("User not found for id: %d", id));
		}
	}

	/**
	 * Gets a list of applications a user has access to
	 * 
	 * @return List of managers
	 * @throws Exception
	 * @since May 13, 2020
	 */
	public List<Application> getUserApps() throws Exception {
		return getUserApps(jwtHolder.getRequiredUserId());
	}

	/**
	 * End point to a get a list of users apps that they have access too
	 * 
	 * @return List of Application objects {@link Application}
	 * @throws Exception
	 * @since May 13, 2020
	 */
	public List<Application> getUserApps(int id) throws Exception {
		return sqlClient.getPage(getSql("getApplications"), params("id", id), APPLICATION_MAPPER);
	}

	/**
	 * Get the regional of the passed in store ID
	 * 
	 * @return The regional of that store
	 * @throws Exception
	 */
	public User getRegionalOfStoreById(String storeId) throws Exception {
		return sqlClient.getTemplate(getSql("getRegionalOfStore"), params("storeId", storeId), USER_MAPPER);
	}

	/**
	 * Get the manager of the passed in store ID.
	 * 
	 * @return The manager of that store
	 * @throws Exception
	 */
	public User getManagerOfStoreById(String storeId) throws Exception {
		return sqlClient.getTemplate(getSql("getManagerOfStoreById"), params("storeId", storeId), USER_MAPPER);
	}

	/**
	 * Creates a new user for the given user object.
	 * 
	 * @param user The user to create.
	 * @return {@link User} object of the users data.
	 * @throws Exception
	 */
	public User insertUser(User user) throws Exception {
		int userId = sqlClient.post(getSql("insertUser"), params("firstName", user.getFirstName())
				.addValue("lastName", user.getLastName()).addValue("email", user.getEmail())
				.addValue("webRoleId", user.getWebRole().id()).addValue("storeId", user.getStoreId())
				.addValue("hireDate",
						user.getHireDate() == null ? LocalDate.now().toString() : user.getHireDate().toString()))
				.get();
		user.setId(userId);
		return user;
	}

	/**
	 * Delete the user for the given id.
	 * 
	 * @param id The id of the user being deleted
	 */
	public void deleteUser(int id) throws Exception {
		sqlClient.delete(getSql("deleteUser"), params("id", id));
	}

	/**
	 * Update the user for the given user object. Null out password field so that it
	 * is not returned on the {@link User} object
	 * 
	 * @param userId Id of the usre being updated.
	 * @param user   what information on the user needs to be updated.
	 * @return user associated to that id with the updated information
	 * @throws Exception
	 */
	public User updateUserProfile(int userId, User user) throws Exception {
		User userProfile = getUserById(userId);

		user.setPassword(null);
		user = mapNonNullUserFields(user, userProfile);

		sqlClient.update(getSql("updateUserProfile"),
				params("firstName", user.getFirstName()).addValue("lastName", user.getLastName())
						.addValue("email", user.getEmail()).addValue("storeId", user.getStoreId())
						.addValue("webRole", user.getWebRole().id()).addValue("hireDate", user.getHireDate())
						.addValue("id", userProfile.getId())
						.addValue("notificationsEnabled", user.isNotificationsEnabled()));

		return getUserById(userId);
	}

	/**
	 * Maps non null user fields from the source to the desitnation.
	 * 
	 * @param destination Where the null fields should be replaced.
	 * @param source      Where to get the replacements for the null fields.
	 * @return {@link User} with the replaced fields.
	 */
	private User mapNonNullUserFields(User destination, User source) {
		if (destination.getFirstName() == null)
			destination.setFirstName(source.getFirstName());
		if (destination.getLastName() == null)
			destination.setLastName(source.getLastName());
		if (destination.getEmail() == null)
			destination.setEmail(source.getEmail());
		if (destination.getStoreId() == null)
			destination.setStoreId(source.getStoreId());
		if (destination.getWebRole() == null)
			destination.setWebRole(source.getWebRole());
		if (destination.isAppAccess() == null)
			destination.setAppAccess(source.isAppAccess());
		if (destination.isNotificationsEnabled() == null)
			destination.setNotificationsEnabled(source.isNotificationsEnabled());
		if (destination.getHireDate() == null)
			destination.setHireDate(source.getHireDate());
		return destination;
	}
}
