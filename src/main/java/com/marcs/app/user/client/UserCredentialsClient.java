/**
 * Copyright of Marcs App. All rights reserved.
 */
package com.marcs.app.user.client;

import org.springframework.beans.factory.annotation.Autowired;

import com.marcs.annotations.interfaces.Client;
import com.marcs.app.user.client.domain.PasswordUpdate;
import com.marcs.app.user.client.domain.User;
import com.marcs.app.user.service.UserCredentialsService;

/**
 * This class exposes the user endpoint's to other app's to pull data across the
 * platform.
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Client
public class UserCredentialsClient {

    @Autowired
    private UserCredentialsService service;

    /**
     * This will create a new row in the user_credentails database with the given
     * auth password and the id of the user to assign it too. This method will only
     * be called when creating a new user.
     * 
     * @param id       The id of the new user.
     * @param authPass The password that was created.
     */
    public void insertUserPassword(int id, String pass) {
        service.insertUserPassword(id, pass);
    }

    /**
     * This will take in a {@link PasswordUpdate} object that will confirm that the
     * current password matches the database password. If it does then it will
     * update the password to the new password.
     * 
     * @param passUpdate Object the holds the current password and new user password
     *                   to change it too.
     * @return {@link User} object of the user that was updated.
     */
    public User updateUserPassword(PasswordUpdate passUpdate) {
        return service.updateUserPassword(passUpdate);
    }

    /**
     * This will take in a {@link PasswordUpdate} object and a user id that needs
     * the password updated.
     * 
     * @param passUpdate Object the holds the current password and new user password
     *                   to change it too.
     * @return {@link User} object of the user that was updated.
     */
    public User updateUserPasswordById(int id, PasswordUpdate passUpdate) {
        return service.updateUserPasswordById(id, passUpdate);
    }

    /**
     * This will get called when a user has forgotten their password. This will
     * allow them to reset it.
     * 
     * @param newPassword The new password
     * @return {@link User} object of the user that was updated.
     */
    public User resetUserPassword(String newPassword) throws Exception {
        return service.resetUserPassword(newPassword);
    }
}
