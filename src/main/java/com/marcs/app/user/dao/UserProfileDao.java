package com.marcs.app.user.dao;

import static com.marcs.app.user.mapper.ApplicationMapper.APPLICATION_MAPPER;
import static com.marcs.app.user.mapper.UserProfileMapper.USER_MAPPER;

import java.time.LocalDate;
import java.util.List;

import javax.sql.DataSource;

import com.google.common.collect.Sets;
import com.marcs.app.user.client.domain.Application;
import com.marcs.app.user.client.domain.User;
import com.marcs.app.user.client.domain.request.UserGetRequest;
import com.marcs.common.abstracts.BaseDao;
import com.marcs.common.exceptions.UserNotFoundException;
import com.marcs.jwt.utility.JwtHolder;
import com.marcs.sql.SqlParamBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

/**
 * Class that handles all the dao calls to the database for users
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Repository
public class UserProfileDao extends BaseDao {

	@Autowired
	private JwtHolder jwtHolder;

	@Autowired
	public UserProfileDao(DataSource source) {
		super(source);
	}

	/**
	 * Get users based on given request filter
	 * 
	 * @param request of the user
	 * @return User profile object {@link User}
	 * @throws Exception
	 */
	public List<User> getUsers(UserGetRequest request) throws Exception {
		SqlParamBuilder builder = SqlParamBuilder.with(request).useAllParams().withParam("id", request.getId())
				.withParam("email", request.getEmail()).withParam("storeId",
						request.getStoreId())
				.withParam("regionalId", request.getRegionalId()).withParam("firstName",
						request.getFirstName())
				.withParam("lastName", request.getLastName()).withParam("storeName",
						request.getStoreName())
				.withParam("notificationsEnabled", request.getNotificationsEnabled())
				.withParamTextEnumCollection("accountStatus", request.getAccountStatus())
				.withParam("excludedUserIds", request.getExcludedUserIds());
		MapSqlParameterSource params = builder.build();
		return getPage(getSql("getUsers", params), params, USER_MAPPER);
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
			UserGetRequest request = new UserGetRequest();
			request.setId(Sets.newHashSet(id));
			return getUsers(request).get(0);
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
		MapSqlParameterSource params = parameterSource("id", id);
		return getPage(getSql("getApplications", params), params, APPLICATION_MAPPER);
	}

	/**
	 * Creates a new user for the given user object.
	 * 
	 * @param user The user to create.
	 * @return {@link User} object of the users data.
	 * @throws Exception
	 */
	public User insertUser(User user) throws Exception {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		SqlParamBuilder builder = SqlParamBuilder.with().useAllParams().withParam("firstName", user.getFirstName())
				.withParam("lastName", user.getLastName()).withParam("email", user.getEmail())
				.withParam("webRoleId", user.getWebRole().id()).withParam("storeId", user.getStoreId())
				.withParam("hireDate",
						user.getHireDate() == null ? LocalDate.now().toString() : user.getHireDate().toString());

		MapSqlParameterSource params = builder.build();

		post(getSql("insertUser", params), params, keyHolder);

		user.setId(keyHolder.getKey().intValue());
		return user;
	}

	/**
	 * Delete the user for the given id.
	 * 
	 * @param id The id of the user being deleted
	 */
	public void deleteUser(int id) throws Exception {
		delete(getSql("deleteUser"), parameterSource("id", id));
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

		MapSqlParameterSource params = parameterSource("firstName", user.getFirstName())
				.addValue("lastName", user.getLastName())
				.addValue("email", user.getEmail()).addValue("storeId", user.getStoreId())
				.addValue("webRole", user.getWebRole() == null ? null : user.getWebRole().id())
				.addValue("hireDate", user.getHireDate())
				.addValue("id", userProfile.getId())
				.addValue("notificationsEnabled", user.isNotificationsEnabled());

		update(getSql("updateUserProfile", params), params);

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
