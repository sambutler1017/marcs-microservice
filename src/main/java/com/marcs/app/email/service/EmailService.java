package com.marcs.app.email.service;

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
     * @throws Exception
     */
    public <T> void sendEmail(EmailProcessor<T> p) throws Exception {
        p.process();
    }

    /**
     * This will send a forgot password link to the given user. If the email exists
     * in the database then the link will be sent.
     * 
     * @param email Email to search for and send an email too.
     * @throws Exception
     */
    public void sendForgotPasswordEmail(String email) throws Exception {
        forgotPasswordEmailProcessor.setParams(email);
        sendEmail(forgotPasswordEmailProcessor);
    }

    /**
     * This will send a report to the individual regionals of what managers are on
     * vacation. As well as admins and sitAdmins will get notifications of all users
     * that are on vacation for the given week.
     * 
     * @throws Exception
     */
    public void sendVacationReport() throws Exception {
        sendEmail(vacationReportEmailProcessor);
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
        newUserEmailProcessor.setParams(newUser);
        sendEmail(newUserEmailProcessor);
    }

    /**
     * Email endpoint to send status update of a users account
     * 
     * @param userId The id of the user to send an email update too.
     * @throws Exception
     */
    public void sendUserAccountUpdateStatusEmail(int userId) throws Exception {
        userAccountStatusUpadteEmailProcessor.setParams(userId);
        sendEmail(userAccountStatusUpadteEmailProcessor);
    }

    /**
     * Email to contact the admin of the website with any questions or concerns that
     * there may be.
     * 
     * @param message The message to send to admin.
     * @throws Exception
     */
    public void sendContactAdminEmail(String message) throws Exception {
        contactAdminEmailProcessor.setParams(message);
        sendEmail(contactAdminEmailProcessor);
    }
}
