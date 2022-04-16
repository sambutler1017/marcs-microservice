package com.marcs.app.user.service;

import com.marcs.app.user.client.domain.UserStatus;
import com.marcs.app.user.dao.UserStatusDao;

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
}
