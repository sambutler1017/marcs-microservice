package com.marcs.app.email.client;

import org.springframework.beans.factory.annotation.Autowired;

import com.marcs.annotations.interfaces.Client;
import com.marcs.app.email.rest.EmailController;
import com.marcs.app.user.client.domain.User;

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
     * This will send a forgot password link to the given user. If the email exists
     * in the database then the link will be sent.
     * 
     * @param email Email to search for and send an email too.
     * @throws Exception
     */
    public void forgotPassword(String email) {
        controller.forgotPassword(email);
    }

    /**
     * This will send a report to all roles that are district manager or higher of
     * the vacations for the current week.
     * 
     * @throws Exception
     */
    public void sendVacationReport() {
        controller.sendVacationReport();
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
    public void sendNewUserEmail(User newUser) {
        controller.sendNewUserEmail(newUser);
    }

    /**
     * Email endpoint to send status update of a users account
     * 
     * @param userId The id of the user to send an email update too.
     * @throws Exception
     */
    public void sendUserAccountUpdateStatusEmail(int userId) {
        controller.sendUserAccountUpdateStatusEmail(userId);
    }
}
