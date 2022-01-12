package com.marcs.app.email.rest;

import java.util.List;

import com.marcs.annotations.interfaces.RestApiController;
import com.marcs.app.email.client.domain.UserEmail;
import com.marcs.app.email.service.EmailService;
import com.marcs.app.user.client.domain.User;
import com.marcs.common.enums.AccountStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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
    private EmailService emailService;

    /**
     * {@link UserEmail} object to send a email too. Default from user will be the
     * admin email.
     * 
     * @param userEmail UserEmail object to get the mail properties from
     * @return {@link UserEmail} object with the time it sent.
     * @throws Exception
     */
    @PostMapping()
    public UserEmail sendEmail(@RequestBody UserEmail userEmail) throws Exception {
        userEmail.setFrom("marcsapp@outlook.com");
        return emailService.sendEmail(userEmail, false);
    }

    /**
     * This will send a forgot password link to the given user. If the email exists
     * in the database then the link will be sent.
     * 
     * @param email Email to search for and send an email too.
     * @throws Exception
     */
    @PostMapping("/forgot-password")
    public void forgotPassword(@RequestBody String email) throws Exception {
        emailService.forgotPassword(email);
    }

    /**
     * This will send a report to the individual regionals of what managers are on
     * vacation. As well as admins and sitAdmins will get notifications of all users
     * that are on vacation for the given week.
     * 
     * @throws Exception
     */
    @PostMapping("/report")
    public List<User> sendVacationReport() throws Exception {
        return emailService.sendVacationReport();
    }

    /**
     * This method will be called when a new user creates an account. They will
     * receieve this email to update them on the status of their account and weather
     * or not it has been approved or denied.
     * 
     * @param newUserEmail  The email of the new user that was created.
     * @param regionalEmail The email of the regional that needs to reveiew it.
     * 
     * @throws Exception
     */
    @PostMapping("/new-user")
    public void sendNewUserEmail(User newUser) throws Exception {
        emailService.sendNewUserEmail(newUser);
    }

    /**
     * Email endpoint to send status update of a users account
     * 
     * @param userId The user to send the email too.
     * @param status The status of their account.
     * @throws Exception
     */
    @PostMapping("/{id}/user/{status}/account-update")
    public User sendUserAccountUpdateStatusEmail(@PathVariable int id, @PathVariable AccountStatus status)
            throws Exception {
        return emailService.sendUserAccountUpdateStatusEmail(id, status);
    }
}
