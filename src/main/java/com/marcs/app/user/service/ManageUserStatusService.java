/**
 * Copyright of Marcs App. All rights reserved.
 */
package com.marcs.app.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marcs.app.email.client.EmailClient;
import com.marcs.app.store.client.StoreClient;
import com.marcs.app.user.client.UserProfileClient;
import com.marcs.app.user.client.domain.User;
import com.marcs.app.user.client.domain.UserStatus;
import com.marcs.app.user.dao.UserStatusDao;
import com.marcs.common.enums.WebRole;
import com.marcs.jwt.utility.JwtHolder;

/**
 * Class for handling the service calls to the dao.
 * 
 * @author Sam Butler
 * @since October 9, 2021
 */
@Component
public class ManageUserStatusService {

    @Autowired
    private UserStatusDao dao;

    @Autowired
    private JwtHolder jwtHolder;

    @Autowired
    private EmailClient emailClient;

    @Autowired
    private StoreClient storeClient;

    @Autowired
    private UserProfileClient userProfileClient;

    /**
     * Inserts the given user status object into the db
     * 
     * @param userStatus Object to be inserted.
     * @return {@link UserStatus} object
     * @throws Exception
     */
    public UserStatus insertUserStatus(UserStatus userStatus) {
        return dao.insertUserStatus(userStatus, userStatus.getUserId());
    }

    /**
     * Updates a users status for the given user id
     * 
     * @param id The id of the user to get the status for.
     * @return {@link UserStatus} object
     * @throws Exception
     */
    public UserStatus updateUserStatusByUserId(int id, UserStatus userStatus) {
        userStatus.setUpdatedUserId(jwtHolder.getUserId());
        UserStatus updatedStatus = dao.updateUserStatusByUserId(id, userStatus);
        currentStoreManagerCheck(id);
        emailClient.sendUserAccountUpdateStatusEmail(id);
        return updatedStatus;
    }

    /**
     * Updates a users app access for the given user id
     * 
     * @param id        The id of the user to get the status for.
     * @param appAccess boolean determining what access the user has.
     * @return {@link UserStatus} object
     * @throws Exception
     */
    public UserStatus updateUserAppAccessByUserId(int id, Boolean appAccess) {
        return dao.updateUserStatusByUserId(id, new UserStatus(jwtHolder.getUserId(), null, appAccess));
    }

    /**
     * This will do a check on store manager if there is already a store manage of
     * an existing store. If the current user being approved is a store manager then
     * it will update the store the user is at as that store manager. Otherwise it
     * will ignore this call.
     * 
     * @param userId The id of the user to check
     * @throws Exception
     */
    private void currentStoreManagerCheck(int userId) {
        User user = userProfileClient.getUserById(userId);
        if(user.getWebRole().equals(WebRole.STORE_MANAGER)) {
            storeClient.updateStoreManagerOfStore(user.getId(), user.getStoreId());
        }
    }
}
