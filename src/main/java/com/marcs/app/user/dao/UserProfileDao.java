/**
 * Copyright of Marcs App. All rights reserved.
 */
package com.marcs.app.user.dao;

import static com.marcs.app.user.mapper.ApplicationMapper.*;
import static com.marcs.app.user.mapper.UserProfileMapper.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.marcs.app.user.client.domain.Application;
import com.marcs.app.user.client.domain.User;
import com.marcs.app.user.client.domain.request.UserGetRequest;
import com.marcs.common.abstracts.BaseDao;
import com.marcs.common.datetime.TimeZoneUtil;
import com.marcs.common.page.Page;
import com.marcs.sql.SqlParamBuilder;

/**
 * Class that handles all the dao calls to the database for users
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Repository
public class UserProfileDao extends BaseDao {

	public UserProfileDao(DataSource source) {
		super(source);
	}

	/**
	 * Get users based on given request filter
	 * 
	 * @param request of the user
	 * @return User profile object {@link User}
	 */
	public Page<User> getUsers(UserGetRequest request) {
		MapSqlParameterSource params = SqlParamBuilder.with(request).useAllParams().withParam(ID, request.getId())
				.withParam(EMAIL, request.getEmail()).withParam(STORE_ID, request.getStoreId())
				.withParam(REGIONAL_MANAGER_ID, request.getRegionalManagerId())
				.withParam(FIRST_NAME, request.getFirstName()).withParam(LAST_NAME, request.getLastName())
				.withParam(STORE_NAME, request.getStoreName())
				.withParam(EMAIL_REPORTS_ENABLED, request.getEmailReportsEnabled())
				.withParam(EXCLUDED_USER_IDS, request.getExcludedUserIds())
				.withParamTextEnumCollection(ACCOUNT_STATUS, request.getAccountStatus())
				.withParamTextEnumCollection(WEB_ROLE_TEXT_ID, request.getWebRole()).build();

		return getPage("getUsersPage", params, USER_MAPPER);
	}

	/**
	 * Get a user by id. It will return an optional object incase that user is not
	 * found.
	 * 
	 * @param userId The user id to search for.
	 * @return Optional User profile object {@link User}
	 */
	public Optional<User> getUserById(int userId) {
		MapSqlParameterSource params = SqlParamBuilder.with().withParam(ID, userId).build();
		return getOptional("getUserById", params, USER_MAPPER);
	}

	/**
	 * End point to a get a list of users apps that they have access too
	 * 
	 * @param userId The user id to get the apps for.
	 * @return List of Application objects {@link Application}
	 */
	public List<Application> getUserApps(int userId) {
		MapSqlParameterSource params = parameterSource(USER_ID, userId);
		return getList("getApplications", params, APPLICATION_MAPPER);
	}

	/**
	 * Creates a new user for the given user object.
	 * 
	 * @param user The user to create.
	 * @return {@link User} object of the users data.
	 */
	public User insertUser(User user) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		MapSqlParameterSource params = SqlParamBuilder.with().useAllParams().withParam(FIRST_NAME, user.getFirstName())
				.withParam(LAST_NAME, user.getLastName()).withParam(EMAIL, user.getEmail())
				.withParam(WEB_ROLE_ID, user.getWebRole().getRank()).withParam(STORE_ID, user.getStoreId())
				.withParamDefault(HIRE_DATE, user.getHireDate()).build();

		post("insertUser", params, keyHolder);
		user.setId(keyHolder.getKey().intValue());
		return user;
	}

	/**
	 * Update the user for the given user object. Null out password field so that it
	 * is not returned on the {@link User} object
	 * 
	 * @param userId     Id of the usre being updated.
	 * @param updateInfo The user information to be updated.
	 */
	public void updateUserProfile(int userId, User updateInfo) {
		updateInfo.setPassword(null);
		MapSqlParameterSource params = SqlParamBuilder.with().withParam(FIRST_NAME, updateInfo.getFirstName())
				.withParam(LAST_NAME, updateInfo.getLastName()).withParam(EMAIL, updateInfo.getEmail())
				.withParam(STORE_ID, StringUtils.hasText(updateInfo.getStoreId()) ? updateInfo.getStoreId() : null)
				.withParam(WEB_ROLE_ID, updateInfo.getWebRole() == null ? null : updateInfo.getWebRole().getRank())
				.withParam(HIRE_DATE, updateInfo.getHireDate()).withParam(ID, userId)
				.withParam(EMAIL_REPORTS_ENABLED, updateInfo.isEmailReportsEnabled()).build();

		update("updateUserProfile", params);
	}

	/**
	 * Method that will update the user's last login time to current date and time;
	 * 
	 * @param userId The user Id to be updated.
	 * @return The user object with the updated information.
	 */
	public void updateUserLastLoginToNow(int userId) {
		MapSqlParameterSource params = SqlParamBuilder.with()
				.withParam(LAST_LOGIN_DATE, LocalDateTime.now(TimeZoneUtil.SYSTEM_ZONE)).withParam(ID, userId).build();
		update("updateUserLastLoginToNow", params);
	}

	/**
	 * Will patch a user for the given id.
	 * 
	 * @param id   of the user
	 * @param user The user info to be updated
	 * @return user associated to that id with the updated information
	 */
	public void patchUserProfileById(int id, User user) {
		user.setPassword(null);
		MapSqlParameterSource params = SqlParamBuilder.with().withParam(FIRST_NAME, user.getFirstName())
				.withParam(LAST_NAME, user.getLastName()).withParam(EMAIL, user.getEmail())
				.withParam(STORE_ID, StringUtils.hasText(user.getStoreId()) ? user.getStoreId() : null)
				.withParam(WEB_ROLE_ID, user.getWebRole() == null ? null : user.getWebRole().getRank())
				.withParam(HIRE_DATE, user.getHireDate()).withParam(ID, id)
				.withParam(EMAIL_REPORTS_ENABLED, user.isEmailReportsEnabled()).build();

		update("patchUserProfile", params);
	}

	/**
	 * Delete the user for the given id.
	 * 
	 * @param id The id of the user being deleted
	 */
	public void deleteUser(int id) {
		delete("deleteUser", parameterSource(ID, id));
	}
}
