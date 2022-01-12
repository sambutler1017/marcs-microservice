package com.marcs.app.user.service;

import com.marcs.app.email.client.EmailClient;
import com.marcs.app.user.client.domain.UserStatus;
import com.marcs.app.user.dao.UserStatusDao;
import com.marcs.jwt.utility.JwtHolder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Class for handling the service calls to the dao.
 * 
 * @author Sam Butler
 * @since October 9, 2021
 */
@Component
public class UserStatusService {

    @Autowired
    private UserStatusDao dao;

    @Autowired
    private JwtHolder jwtHolder;

    @Autowired
    private EmailClient emailClient;

    /**
     * Gets the status for the given user id.
     * 
     * @param userId The id of the user to get the status for.
     * @return {@link UserStatus} object
     * @throws Exception
     */
    public UserStatus getUserStatusById(int userId) throws Exception {
        return dao.getUserStatusById(userId);
    }

    /**
     * Inserts the given user status object into the db
     * 
     * @param userStatus Object to be inserted.
     * @return {@link UserStatus} object
     * @throws Exception
     */
    public UserStatus insertUserStatus(UserStatus userStatus) throws Exception {
        return dao.insertUserStatus(userStatus, jwtHolder.getRequiredUserId());
    }

    /**
     * Updates a users status for the given user id
     * 
     * @param id The id of the user to get the status for.
     * @return {@link UserStatus} object
     * @throws Exception
     */
    public UserStatus updateUserStatusByUserId(int id, UserStatus userStatus) throws Exception {
        userStatus.setUpdatedUserId(this.jwtHolder.getRequiredUserId());
        emailClient.sendUserAccountUpdateStatusEmail(id, userStatus.getAccountStatus());
        return dao.updateUserStatusByUserId(id, userStatus);
    }
}
