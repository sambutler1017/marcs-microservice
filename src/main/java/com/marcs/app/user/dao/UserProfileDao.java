package com.marcs.app.user.dao;

import static com.marcs.app.user.mapper.ApplicationMapper.*;
import static com.marcs.app.user.mapper.UserProfileMapper.*;

import java.time.LocalDateTime;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.marcs.app.user.client.domain.Application;
import com.marcs.app.user.client.domain.User;
import com.marcs.app.user.client.domain.request.UserGetRequest;
import com.marcs.common.abstracts.BaseDao;
import com.marcs.common.date.TimeZoneUtil;
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
				.withParam(REGIONAL_ID, request.getRegionalId()).withParam(FIRST_NAME, request.getFirstName())
				.withParam(LAST_NAME, request.getLastName()).withParam(STORE_NAME, request.getStoreName())
				.withParam(EMAIL_REPORTS_ENABLED, request.getEmailReportsEnabled())
				.withParam(EXCLUDED_USER_IDS, request.getExcludedUserIds())
				.withParamTextEnumCollection(ACCOUNT_STATUS, request.getAccountStatus())
				.withParamTextEnumCollection(WEB_ROLE_TEXT_ID, request.getWebRole()).build();

		return getPage("getUsersPage", params, USER_MAPPER);
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
	 * @param userId      Id of the usre being updated.
	 * @param updateInfo  The user information to be updated.
	 * @param currentInfo The current user info.
	 */
	public void updateUserProfile(int userId, User updateInfo, User currentInfo) {
		updateInfo.setPassword(null);
		updateInfo = mapNonNullUserFields(updateInfo, currentInfo);

		MapSqlParameterSource params = SqlParamBuilder.with().withParam(FIRST_NAME, updateInfo.getFirstName())
				.withParam(LAST_NAME, updateInfo.getLastName()).withParam(EMAIL, updateInfo.getEmail())
				.withParam(STORE_ID, updateInfo.getStoreId().trim() == "" ? null : updateInfo.getStoreId())
				.withParam(WEB_ROLE_ID, updateInfo.getWebRole().getRank())
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
	 * Delete the user for the given id.
	 * 
	 * @param id The id of the user being deleted
	 */
	public void deleteUser(int id) {
		delete("deleteUser", parameterSource(ID, id));
	}

	/**
	 * Maps non null user fields from the source to the desitnation.
	 * 
	 * @param destination Where the null fields should be replaced.
	 * @param source      Where to get the replacements for the null fields.
	 * @return {@link User} with the replaced fields.
	 */
	private User mapNonNullUserFields(User destination, User source) {
		if(destination.getFirstName() == null) destination.setFirstName(source.getFirstName());
		if(destination.getLastName() == null) destination.setLastName(source.getLastName());
		if(destination.getEmail() == null) destination.setEmail(source.getEmail());
		if(destination.getStoreId() == null) destination.setStoreId(source.getStoreId());
		if(destination.getWebRole() == null) destination.setWebRole(source.getWebRole());
		if(destination.isAppAccess() == null) destination.setAppAccess(source.isAppAccess());
		if(destination.isEmailReportsEnabled() == null)
			destination.setEmailReportsEnabled(source.isEmailReportsEnabled());
		if(destination.getHireDate() == null) destination.setHireDate(source.getHireDate());
		return destination;
	}
}
