package com.marcs.app.user.dao;

import static com.marcs.app.user.mapper.UserStatusMapper.*;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.marcs.app.user.client.domain.UserStatus;
import com.marcs.common.abstracts.BaseDao;
import com.marcs.sql.SqlParamBuilder;

/**
 * Class for handling the dao calls for a user status
 * 
 * @author Sam Butler
 * @since October 9, 2021
 */
@Repository
public class UserStatusDao extends BaseDao {

    public UserStatusDao(DataSource source) {
        super(source);
    }

    /**
     * Gets the status for the given user id.
     * 
     * @param userId The id of the user to get the status for.
     * @return {@link UserStatus} object
     * @throws Exception
     */
    public UserStatus getUserStatusById(int userId) {
        return get("getUserStatusById", parameterSource(USER_ID, userId), USER_STATUS_MAPPER);
    }

    /**
     * Inserts the given user status object into the db
     * 
     * @param userStatus     Object to be inserted.
     * @param updatingUserId The user that has updated the user account status last.
     * @return {@link UserStatus} object
     * @throws Exception
     */
    public UserStatus insertUserStatus(UserStatus userStatus, int updatingUserId) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = SqlParamBuilder.with().withParam(USER_ID, userStatus.getUserId())
                .withParam(ACCOUNT_STATUS, userStatus.getAccountStatus())
                .withParam(APP_ACCESS, userStatus.isAppAccess())
                .withParam(UPDATE_USER_ID, updatingUserId == -1 ? null : updatingUserId).build();

        post("insertUserStatus", params, keyHolder);

        userStatus.setRequestId(keyHolder.getKey().intValue());
        return userStatus;
    }

    /**
     * Updates a users status for the given user id
     * 
     * @param id The id of the user to get the status for.
     * @return {@link UserStatus} object
     * @throws Exception
     */
    public UserStatus updateUserStatusByUserId(int id, UserStatus userStatus) {
        UserStatus currentStatus = getUserStatusById(id);
        userStatus = mapNonNullUserStatusFields(userStatus, currentStatus);

        MapSqlParameterSource params = SqlParamBuilder.with().withParam(USER_ID, id)
                .withParam(ACCOUNT_STATUS, userStatus.getAccountStatus())
                .withParam(APP_ACCESS, userStatus.isAppAccess())
                .withParam(UPDATE_USER_ID, userStatus.getUpdatedUserId()).build();

        update("updateUserStatus", params);

        return getUserStatusById(id);
    }

    /**
     * Maps non null user fields from the source to the desitnation.
     * 
     * @param destination Where the null fields should be replaced.
     * @param source      Where to get the replacements for the null fields.
     * @return {@link UserStatus} with the replaced fields.
     */
    private UserStatus mapNonNullUserStatusFields(UserStatus destination, UserStatus source) {
        if(destination.getAccountStatus() == null) destination.setAccountStatus(source.getAccountStatus());
        if((destination.getUpdatedUserId() == null || destination.getUpdatedUserId() == 0)
           && source.getUpdatedUserId() != 0) destination.setUpdatedUserId(source.getUpdatedUserId());
        if(destination.isAppAccess() == null) destination.setAppAccess(source.isAppAccess());
        return destination;
    }
}
