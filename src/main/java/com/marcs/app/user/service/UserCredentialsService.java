package com.marcs.app.user.service;

import com.marcs.app.auth.client.domain.AuthPassword;
import com.marcs.app.user.client.domain.User;
import com.marcs.app.user.dao.UserCredentialsDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * User Service class that handles all service calls to the
 * {@link UserCredentialsDao}
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Component
public class UserCredentialsService {

    @Autowired
    private UserCredentialsDao dao;

    /**
     * This will be called when new users are created so that they have default
     * passwords. This will only be called when someone else is creating a user
     * account for someone.
     * 
     * @param userId   The id to add the password for.
     * @param authPass Contains the hashed password and salt value.
     * @throws Exception
     */
    public void insertUserPassword(int userId, AuthPassword authPass) throws Exception {
        dao.insertUserPassword(userId, authPass);
    }

    /**
     * Update the users password, for the given password.
     * 
     * @param userId   Id of the use that is being updated.
     * @param password The password to set on the user profile.
     * @param salt     The salt value that was appended to the password.
     * @return user associated to that id with the updated information
     * @throws Exception
     */
    public User updateUserPassword(int userId, AuthPassword authPassword) throws Exception {
        return dao.updateUserPassword(userId, authPassword);
    }
}
