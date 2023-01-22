package com.marcs.app.email.processors;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.marcs.app.email.client.domain.UserEmail;
import com.marcs.common.date.TimeZoneUtil;
import com.marcs.environment.AppEnvironmentService;
import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

/**
 * Base Email processor
 * 
 * @author Sam Butler
 * @since December 20, 2022
 */
public abstract class EmailProcessor<T> {
    protected final String MARCS_FROM = "marcsapp@outlook.com";
    protected final String BASE_HTML_PATH = "src/main/java/com/marcs/app/email/client/domain/HTMLTemplates";

    protected final static Logger LOGGER = LoggerFactory.getLogger(EmailProcessor.class);

    @Autowired
    private AppEnvironmentService appEnvironmentService;

    /**
     * Process the generic email type.
     * 
     * @return {@link UserEmail} object to send.
     * @throws Exception
     */
    public abstract List<UserEmail> process();

    /**
     * Set any params to be passed in with the email to be processed.
     */
    public abstract void setParams(T params);

    /**
     * Overloaded method to send an email with the given to, subject, and body of
     * the email to be sent.
     * 
     * @param to      The email to send too.
     * @param subject Subject of the email
     * @param body    What to include in the email
     * @return The user email that was created.
     */
    protected UserEmail send(String to, String subject, String body) {
        UserEmail userEmail = buildUserEmail(to, subject, body);
        return send(userEmail);
    }

    /**
     * {@link UserEmail} object to send a email too. Default from user will be the
     * admin email. Common method for sending an email.
     * 
     * @param userEmail UserEmail object to get the mail properties from
     * @return {@link UserEmail} object with the time it sent.
     */
    protected UserEmail send(UserEmail userEmail) {
        Content content = new Content("text/html", userEmail.getBody());
        Mail mail = new Mail(userEmail.getFrom(), userEmail.getSubject(), userEmail.getRecipient(), content);

        try {
            SendGrid sg = new SendGrid(appEnvironmentService.getSendGridSigningKey());
            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response res = sg.api(request);
            LOGGER.info("Email sent to '{}' with status code : {}", userEmail.getRecipient().getEmail(),
                        res.getStatusCode());
        }
        catch(IOException e) {
            LOGGER.info("Email could not be sent. Error processing Email");
        }

        userEmail.setSentDate(LocalDateTime.now(TimeZoneUtil.SYSTEM_ZONE));
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
        userEmail.setFrom(new Email(MARCS_FROM));
        userEmail.setRecipient(new Email(to));
        userEmail.setSubject(subject);
        userEmail.setBody(body);
        return userEmail;
    }

    /**
     * Method for processing email templates and returning their content.
     * 
     * @param fileName The file name to pull.
     * @return The String of the read email template.
     */
    protected String readEmailTemplate(String fileName) {
        final String filePath = String.format("%s/%s", BASE_HTML_PATH, fileName);
        String emailContent = "";

        try(final BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            emailContent = br.lines().collect(Collectors.joining(" "));
        }
        catch(IOException e) {
            LOGGER.warn("Could not process email template: '{}'", fileName);
        }

        return emailContent;
    }
}
