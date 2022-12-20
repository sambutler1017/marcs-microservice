package com.marcs.app.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marcs.app.email.client.EmailClient;
import com.marcs.app.user.client.domain.UserStatus;
import com.marcs.app.user.dao.UserStatusDao;
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

    /**
     * Inserts the given user status object into the db
     * 
     * @param userStatus Object to be inserted.
     * @return {@link UserStatus} object
     * @throws Exception
     */
    public UserStatus insertUserStatus(UserStatus userStatus) throws Exception {
        return dao.insertUserStatus(userStatus, userStatus.getUserId());
    }

    /**
     * Updates a users status for the given user id
     * 
     * @param id The id of the user to get the status for.
     * @return {@link UserStatus} object
     * @throws Exception
     */
    public UserStatus updateUserStatusByUserId(int id, UserStatus userStatus) throws Exception {
        userStatus.setUpdatedUserId(jwtHolder.getUserId());
        UserStatus updatedStatus = dao.updateUserStatusByUserId(id, userStatus);
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
    public UserStatus updateUserAppAccessByUserId(int id, Boolean appAccess) throws Exception {
        return dao.updateUserStatusByUserId(id, new UserStatus(jwtHolder.getUserId(), null, appAccess));
    }
}
