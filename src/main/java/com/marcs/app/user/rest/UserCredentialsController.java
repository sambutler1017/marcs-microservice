/**
 * Copyright of Marcs App. All rights reserved.
 */
package com.marcs.app.user.rest;

import static org.springframework.http.MediaType.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marcs.app.user.client.domain.PasswordUpdate;
import com.marcs.app.user.client.domain.User;
import com.marcs.app.user.service.UserCredentialsService;

@RequestMapping("/api/users/credentials")
@RestController
public class UserCredentialsController {

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
    public void insertUserPassword(@PathVariable int id, @RequestBody String pass) {
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
     * @throws Exception If the user can not be authenticated or it failed to hash
     *                   the new password.
     */
    @PutMapping(path = "/password", produces = APPLICATION_JSON_VALUE)
    public User updateUserPassword(@RequestBody PasswordUpdate passUpdate) {
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
    @PutMapping(path = "/password/{id}", produces = APPLICATION_JSON_VALUE)
    public User updateUserPasswordById(@PathVariable int id, @RequestBody PasswordUpdate passUpdate) {
        return service.updateUserPasswordById(id, passUpdate);
    }

    /**
     * This will get called when a user has forgotten their password. This will
     * allow them to reset it.
     * 
     * @param passUpdate Object the holds the current password and new user password
     *                   to change it too.
     * @return {@link User} object of the user that was updated.
     * @throws Exception If the user can not be authenticated or it failed to hash
     *                   the new password.
     */
    @PutMapping(path = "/password/reset", produces = APPLICATION_JSON_VALUE)
    public User resetUserPassword(@RequestBody PasswordUpdate passUpdate) throws Exception {
        return service.resetUserPassword(passUpdate.getNewPassword());
    }
}
