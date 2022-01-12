package com.marcs.app.email.client;

import java.util.List;

import com.marcs.annotations.interfaces.Client;
import com.marcs.app.email.client.domain.UserEmail;
import com.marcs.app.email.rest.EmailController;
import com.marcs.app.user.client.domain.User;
import com.marcs.common.enums.AccountStatus;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Class to expose the email client to other services.
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Client
public class EmailClient {

    @Autowired
    private EmailController controller;

    /**
     * {@link UserEmail} object to send a email too. Default from user will be the
     * admin email.
     * 
     * @param userEmail UserEmail object to get the mail properties from
     * @return {@link UserEmail} object with the time it sent.
     * @throws Exception
     */
    public UserEmail sendEmail(UserEmail userEmail) throws Exception {
        return controller.sendEmail(userEmail);
    }

    /**
     * This will send a forgot password link to the given user. If the email exists
     * in the database then the link will be sent.
     * 
     * @param email Email to search for and send an email too.
     * @throws Exception
     */
    public void forgotPassword(String email) throws Exception {
        controller.forgotPassword(email);
    }

    /**
     * This will send a report to all roles that are district manager or higher of
     * the vacations for the current week.
     * 
     * @throws Exception
     */
    public List<User> sendVacationReport() throws Exception {
        return controller.sendVacationReport();
    }

    /**
     * This method will be called when a new user creates an account. They will
     * receieve this email to update them on the status of their account and weather
     * or not it has been approved or denied.
     * 
     * @param newUser The new user that was created.
     * 
     * @throws Exception
     */
    public void sendNewUserEmail(User newUser) throws Exception {
        controller.sendNewUserEmail(newUser);
    }

    /**
     * Email endpoint to send status update of a users account
     * 
     * @param userId The user to send the email too.
     * @param status The status of their account.
     * @throws Exception
     */
    public User sendUserAccountUpdateStatusEmail(int id, AccountStatus status)
            throws Exception {
        return controller.sendUserAccountUpdateStatusEmail(id, status);
    }
}
