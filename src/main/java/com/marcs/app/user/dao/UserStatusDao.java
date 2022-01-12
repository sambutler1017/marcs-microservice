package com.marcs.app.user.dao;

import static com.marcs.app.user.mapper.UserStatusMapper.USER_STATUS_MAPPER;

import com.marcs.app.user.client.domain.UserStatus;
import com.marcs.common.abstracts.AbstractSqlDao;

import org.springframework.stereotype.Repository;

/**
 * Class for handling the dao calls for a user status
 * 
 * @author Sam Butler
 * @since October 9, 2021
 */
@Repository
public class UserStatusDao extends AbstractSqlDao {

    /**
     * Gets the status for the given user id.
     * 
     * @param userId The id of the user to get the status for.
     * @return {@link UserStatus} object
     * @throws Exception
     */
    public UserStatus getUserStatusById(int userId) throws Exception {
        return sqlClient.getTemplate(getSql("getUserStatusById"), params("userId", userId), USER_STATUS_MAPPER);
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
        int requestId = sqlClient.post(getSql("insertUserStatus"),
                params("userId", userStatus.getUserId()).addValue("accountStatus", userStatus.getAccountStatus())
                        .addValue("appAccess", userStatus.isAppAccess())
                        .addValue("updatedUserId", updatingUserId == -1 ? null : updatingUserId))
                .get();
        userStatus.setRequestId(requestId);
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

        sqlClient.update(getSql("updateUserStatus"),
                params("userId", id).addValue("appAccess", userStatus.isAppAccess())
                        .addValue("accountStatus", userStatus.getAccountStatus())
                        .addValue("updatedUserId", userStatus.getUpdatedUserId()));

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
