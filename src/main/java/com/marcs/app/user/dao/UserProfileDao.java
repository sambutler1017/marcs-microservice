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
		MapSqlParameterSource params = SqlParamBuilder.with(request).useAllParams()
				.withParam(ID, request.getId())
				.withParam(EMAIL, request.getEmail())
				.withParam(STORE_ID, request.getStoreId())
				.withParam(REGIONAL_ID, request.getRegionalId())
				.withParam(FIRST_NAME, request.getFirstName())
				.withParam(LAST_NAME, request.getLastName())
				.withParam(STORE_NAME, request.getStoreName())
				.withParam(EMAIL_REPORTS_ENABLED, request.getEmailReportsEnabled())
				.withParam("excludedUserIds", request.getExcludedUserIds())
				.withParamTextEnumCollection(ACCOUNT_STATUS, request.getAccountStatus())
				.withParamTextEnumCollection(WEB_ROLE_TEXT_ID, request.getWebRole()).build();

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
	 * End point to a get a list of users apps that they have access too
	 * 
	 * @param userId The user id to get the apps for.
	 * @return List of Application objects {@link Application}
	 * @throws Exception
	 * @since May 13, 2020
	 */
	public List<Application> getUserApps(int userId) throws Exception {
		MapSqlParameterSource params = parameterSource(USER_ID, userId);
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
		SqlParamBuilder builder = SqlParamBuilder.with().useAllParams()
				.withParam(FIRST_NAME, user.getFirstName())
				.withParam(LAST_NAME, user.getLastName())
				.withParam(EMAIL, user.getEmail())
				.withParam(WEB_ROLE_ID, user.getWebRole().getRank())
				.withParam(STORE_ID, user.getStoreId().trim() == "" ? null : user.getStoreId())
				.withParam(HIRE_DATE,
						user.getHireDate() == null ? LocalDate.now().toString() : user.getHireDate().toString());

		MapSqlParameterSource params = builder.build();

		post(getSql("insertUser", params), params, keyHolder);

		user.setId(keyHolder.getKey().intValue());
		return user;
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

		MapSqlParameterSource params = SqlParamBuilder.with()
				.withParam(FIRST_NAME, user.getFirstName())
				.withParam(LAST_NAME, user.getLastName())
				.withParam(EMAIL, user.getEmail())
				.withParam(STORE_ID, user.getStoreId().trim() == "" ? null : user.getStoreId())
				.withParam(WEB_ROLE_ID, user.getWebRole().getRank())
				.withParam(HIRE_DATE, user.getHireDate())
				.withParam(ID, userId)
				.withParam(EMAIL_REPORTS_ENABLED, user.isEmailReportsEnabled()).build();

		update(getSql("updateUserProfile", params), params);

		return getUserById(userId);
	}

	/**
	 * Delete the user for the given id.
	 * 
	 * @param id The id of the user being deleted
	 */
	public void deleteUser(int id) throws Exception {
		delete(getSql("deleteUser"), parameterSource(ID, id));
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
		if (destination.isEmailReportsEnabled() == null)
			destination.setEmailReportsEnabled(source.isEmailReportsEnabled());
		if (destination.getHireDate() == null)
			destination.setHireDate(source.getHireDate());
		return destination;
	}
}
