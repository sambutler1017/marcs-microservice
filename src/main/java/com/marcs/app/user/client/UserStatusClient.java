package com.marcs.app.user.client;

import com.marcs.annotations.interfaces.Client;
import com.marcs.app.user.client.domain.UserStatus;
import com.marcs.app.user.rest.UserStatusController;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * This class exposes the user endpoint's to other app's to pull data across the
 * platform.
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Client
public class UserStatusClient {
    @Autowired
    private UserStatusController controller;

    /**
     * Gets the status for the given user id.
     * 
     * @param id The id of the user to get the status for.
     * @return {@link UserStatus} object
     * @throws Exception
     */
    public UserStatus getUserStatusById(int id) throws Exception {
        return controller.getUserStatusById(id);
    }

    /**
     * Inserts the given user status object into the db
     * 
     * @param userStatus Object to be inserted.
     * @return {@link UserStatus} object
     * @throws Exception
     */
    public UserStatus insertUserStatus(UserStatus userStatus) throws Exception {
        return controller.insertUserStatus(userStatus);
    }

    /**
     * Updates a users status for the given user id
     * 
     * @param id The id of the user to get the status for.
     * @return {@link UserStatus} object
     * @throws Exception
     */
    public UserStatus updateUserStatusByUserId(int id, UserStatus userStatus)
            throws Exception {
        return controller.updateUserStatusByUserId(id, userStatus);
    }
}
