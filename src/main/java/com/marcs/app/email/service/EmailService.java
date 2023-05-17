/**
 * Copyright of Marcs App. All rights reserved.
 */
package com.marcs.app.email.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marcs.app.email.client.domain.UserEmail;
import com.marcs.app.email.processors.ContactAdminEmailProcessor;
import com.marcs.app.email.processors.EmailProcessor;
import com.marcs.app.email.processors.ForgotPasswordEmailProcessor;
import com.marcs.app.email.processors.NewUserEmailProcessor;
import com.marcs.app.email.processors.UserAccountStatusUpadteEmailProcessor;
import com.marcs.app.email.processors.VacationReportEmailProcessor;
import com.marcs.app.user.client.domain.User;

/**
 * Service class for doing all the dirty work for sending a message.
 * 
 * @author Sam Butler
 * @since August 1, 2021
 */
@Service
public class EmailService {

    @Autowired
    private ContactAdminEmailProcessor contactAdminEmailProcessor;

    @Autowired
    private ForgotPasswordEmailProcessor forgotPasswordEmailProcessor;

    @Autowired
    private NewUserEmailProcessor newUserEmailProcessor;

    @Autowired
    private UserAccountStatusUpadteEmailProcessor userAccountStatusUpadteEmailProcessor;

    @Autowired
    private VacationReportEmailProcessor vacationReportEmailProcessor;

    /**
     * Sends the user email for the given email processor.
     * 
     * @param <T>       The generic email processor type.
     * @param processor The processor to run.
     * @return The {@link UserEmail} object that was sent.
     */
    public <T> List<UserEmail> sendEmail(EmailProcessor<T> p) {
        List<UserEmail> emails = p.process();
        emails.stream().map(e -> {
            e.setBody(null);
            return e;
        }).collect(Collectors.toList());
        return emails;
    }

    /**
     * This will send a forgot password link to the given user. If the email exists
     * in the database then the link will be sent.
     * 
     * @param email Email to search for and send an email too.
     */
    public List<UserEmail> sendForgotPasswordEmail(String email) {
        forgotPasswordEmailProcessor.setParams(email);
        return sendEmail(forgotPasswordEmailProcessor);
    }

    /**
     * This will send a report to the individual regional managers of what managers
     * are on vacation. As well as admins and sitAdmins will get notifications of
     * all users that are on vacation for the given week.
     */
    public List<UserEmail> sendVacationReport() {
        return sendEmail(vacationReportEmailProcessor);
    }

    /**
     * This method will be called when a new user creates an account. They will
     * receieve this email to update them on the status of their account and weather
     * or not it has been approved or denied.
     * 
     * @param newUser The new user that was created.
     */
    public List<UserEmail> sendNewUserEmail(User newUser) {
        newUserEmailProcessor.setParams(newUser);
        return sendEmail(newUserEmailProcessor);
    }

    /**
     * Email endpoint to send status update of a users account
     * 
     * @param userId The id of the user to send an email update too.
     */
    public List<UserEmail> sendUserAccountUpdateStatusEmail(int userId) {
        userAccountStatusUpadteEmailProcessor.setParams(userId);
        return sendEmail(userAccountStatusUpadteEmailProcessor);
    }

    /**
     * Email to contact the admin of the website with any questions or concerns that
     * there may be.
     * 
     * @param message The message to send to admin.
     */
    public List<UserEmail> sendContactAdminEmail(String message) {
        contactAdminEmailProcessor.setParams(message);
        return sendEmail(contactAdminEmailProcessor);
    }
}
