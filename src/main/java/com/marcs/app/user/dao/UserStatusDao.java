package com.marcs.app.user.dao;

import static com.marcs.app.user.mapper.UserStatusMapper.USER_STATUS_MAPPER;

import javax.sql.DataSource;

import com.marcs.app.user.client.domain.UserStatus;
import com.marcs.common.abstracts.BaseDao;
import com.marcs.sql.SqlParamBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

/**
 * Class for handling the dao calls for a user status
 * 
 * @author Sam Butler
 * @since October 9, 2021
 */
@Repository
public class UserStatusDao extends BaseDao {

    @Autowired
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
    public UserStatus getUserStatusById(int userId) throws Exception {
        return get(getSql("getUserStatusById"), parameterSource(USER_ID, userId), USER_STATUS_MAPPER);
    }

    /**
     * Inserts the given user status object into the db
     * 
     * @param userStatus     Object to be inserted.
     * @param updatingUserId The user that has updated the user account status last.
     * @return {@link UserStatus} object
     * @throws Exception
     */
    public UserStatus insertUserStatus(UserStatus userStatus, int updatingUserId) throws Exception {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParamBuilder builder = SqlParamBuilder.with().withParam(USER_ID, userStatus.getUserId())
                .withParam(ACCOUNT_STATUS, userStatus.getAccountStatus())
                .withParam(APP_ACCESS, userStatus.isAppAccess())
                .withParam(UPDATE_USER_ID, updatingUserId == -1 ? null : updatingUserId);

        MapSqlParameterSource params = builder.build();
        post(getSql("insertUserStatus", params), params, keyHolder);

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
    public UserStatus updateUserStatusByUserId(int id, UserStatus userStatus) throws Exception {
        UserStatus currentStatus = getUserStatusById(id);
        userStatus = mapNonNullUserStatusFields(userStatus, currentStatus);

        SqlParamBuilder builder = SqlParamBuilder.with().withParam(USER_ID, id)
                .withParam(ACCOUNT_STATUS, userStatus.getAccountStatus())
                .withParam(APP_ACCESS, userStatus.isAppAccess())
                .withParam(UPDATE_USER_ID, userStatus.getUpdatedUserId());

        MapSqlParameterSource params = builder.build();
        update(getSql("updateUserStatus", params), params);

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
        if (destination.getAccountStatus() == null)
            destination.setAccountStatus(source.getAccountStatus());
        if ((destination.getUpdatedUserId() == null || destination.getUpdatedUserId() == 0)
                && source.getUpdatedUserId() != 0)
            destination.setUpdatedUserId(source.getUpdatedUserId());
        if (destination.isAppAccess() == null)
            destination.setAppAccess(source.isAppAccess());
        return destination;
    }
}
