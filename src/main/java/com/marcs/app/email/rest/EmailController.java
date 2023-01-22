package com.marcs.app.email.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.marcs.annotations.interfaces.RestApiController;
import com.marcs.app.email.client.domain.UserEmail;
import com.marcs.app.email.service.EmailService;
import com.marcs.app.user.client.domain.User;

/**
 * Email Controller for dealing with sending emails to users.
 * 
 * @author Sam Butler
 * @since August 1, 2021
 */
@RequestMapping("api/mail-app/email")
@RestApiController
public class EmailController {
    @Autowired
    private EmailService service;

    /**
     * This will send a forgot password link to the given user. If the email exists
     * in the database then the link will be sent.
     * 
     * @param email Email to search for and send an email too.
     * @throws Exception
     */
    @PostMapping("/forgot-password")
    public List<UserEmail> forgotPassword(@RequestBody String email) {
        return service.sendForgotPasswordEmail(email);
    }

    /**
     * This will send a report to the individual regionals of what managers are on
     * vacation. As well as admins and sitAdmins will get notifications of all users
     * that are on vacation for the given week.
     * 
     * @throws Exception
     */
    @PostMapping("/report")
    public List<UserEmail> sendVacationReport() {
        return service.sendVacationReport();
    }

    /**
     * This method will be called when a new user creates an account. They will
     * receieve this email to update them on the status of their account and weather
     * or not it has been approved or denied.
     * 
     * @param newUser The email of the new user that was created.
     * 
     * @throws Exception
     */
    @PostMapping("/new-user")
    public List<UserEmail> sendNewUserEmail(User newUser) {
        return service.sendNewUserEmail(newUser);
    }

    /**
     * Email endpoint to send status update of a users account
     * 
     * @param userId The user to send the email too.
     * @throws Exception
     */
    @PostMapping("/{id}/user/account-update")
    public List<UserEmail> sendUserAccountUpdateStatusEmail(@PathVariable int userId) {
        return service.sendUserAccountUpdateStatusEmail(userId);
    }

    /**
     * Email to contact the admin of the website with any questions or concerns that
     * there may be.
     * 
     * @param message The message to send to admin.
     * @throws Exception
     */
    @PostMapping("/contact")
    public List<UserEmail> sendContactAdminEmail(@RequestBody String message) {
        return service.sendContactAdminEmail(message);
    }
}
