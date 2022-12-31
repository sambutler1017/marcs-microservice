package com.marcs.app.email.processors;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;

import com.marcs.app.email.client.domain.UserEmail;
import com.marcs.common.util.TimeZoneUtil;
import com.marcs.environment.AppEnvironmentService;
import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;

/**
 * Base Email processor
 * 
 * @author Sam Butler
 * @since December 20, 2022
 */
public abstract class EmailProcessor<T> {
    protected final String BASE_HTML_PATH = "src/main/java/com/marcs/app/email/client/domain/HTMLTemplates";

    @Autowired
    private AppEnvironmentService appEnvironmentService;

    /**
     * Process the generic email type.
     * 
     * @return {@link UserEmail} object to send.
     * @throws Exception
     */
    public abstract void process() throws Exception;

    /**
     * Set any params to be passed in with the email to be processed.
     */
    public abstract void setParams(T params);

    /**
     * {@link UserEmail} object to send a email too. Default from user will be the
     * admin email. Common method for sending an email.
     * 
     * @param userEmail UserEmail object to get the mail properties from
     * @return {@link UserEmail} object with the time it sent.
     * @throws Exception
     */
    protected UserEmail send(UserEmail userEmail) throws Exception {
        Email from = new Email(userEmail.getFrom());
        Email to = new Email(userEmail.getRecipient());
        Content content = new Content("text/html", userEmail.getBody());
        Mail mail = new Mail(from, userEmail.getSubject(), to, content);

        SendGrid sg = new SendGrid(appEnvironmentService.getSendGridSigningKey());
        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());
        sg.api(request);

        userEmail.setSentDate(LocalDateTime.now(TimeZoneUtil.defaultZone()));
        return userEmail;
    }

    /**
     * Builds out a {@link UserEmail} object.
     * 
     * @param to      Who the email is going too.
     * @param subject What the main subject of the email is.
     * @param body    What is contained in the email body
     * @return {@link UserEmail}
     * @see #forgotPassword(String)
     */
    protected UserEmail buildUserEmail(String to, String subject, String body) {
        UserEmail userEmail = new UserEmail();
        userEmail.setFrom("marcsapp@outlook.com");
        userEmail.setRecipient(to);
        userEmail.setSubject(subject);
        userEmail.setBody(body);
        return userEmail;
    }
}
