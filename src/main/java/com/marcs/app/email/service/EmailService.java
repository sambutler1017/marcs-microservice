package com.marcs.app.email.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.mail.MessagingException;

import com.google.common.collect.Sets;
import com.marcs.app.email.client.domain.UserEmail;
import com.marcs.app.user.client.UserProfileClient;
import com.marcs.app.user.client.domain.User;
import com.marcs.app.user.client.domain.request.UserGetRequest;
import com.marcs.app.vacation.client.domain.Vacation;
import com.marcs.app.vacation.client.domain.request.VacationRequest;
import com.marcs.app.vacation.rest.VacationController;
import com.marcs.common.enums.AccountStatus;
import com.marcs.common.enums.VacationStatus;
import com.marcs.common.enums.WebRole;
import com.marcs.common.exceptions.SqlFragmentNotFoundException;
import com.marcs.jwt.utility.JwtTokenUtil;
import com.marcs.service.activeProfile.ActiveProfile;
import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Service class for doing all the dirty work for sending a message.
 * 
 * @author Sam Butler
 * @since August 1, 2021
 */
@Component
public class EmailService {

    private final String RESET_LINK = "https://marcs-app.herokuapp.com/login/reset-password/";
    private final String BASE_HTML_PATH = "src/main/java/com/marcs/app/email/client/domain/HTMLTemplates";

    @Autowired
    private UserProfileClient userClient;

    @Autowired
    private VacationController vcationController;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private ActiveProfile activeProfile;

    /**
     * {@link UserEmail} object to send a email too. Default from user will be the
     * admin email.
     * 
     * @param userEmail UserEmail object to get the mail properties from
     * @return {@link UserEmail} object with the time it sent.
     * @throws Exception
     */
    public UserEmail sendEmail(UserEmail userEmail, boolean html) throws Exception {
        Email from = new Email(userEmail.getFrom());
        Email to = new Email(userEmail.getRecipient());
        Content content = new Content(html ? "text/html" : "text/plain", userEmail.getBody());
        Mail mail = new Mail(from, userEmail.getSubject(), to, content);

        SendGrid sg = new SendGrid(activeProfile.getSendGridSigningKey());
        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());
        sg.api(request);

        userEmail.setSentDate(new Date());
        return userEmail;
    }

    /**
     * This will send a forgot password link to the given user. If the email exists
     * in the database then the link will be sent.
     * 
     * @param email Email to search for and send an email too.
     * @return {@link User} object of the found user
     * @throws MessagingException           If an error occurs processing the
     *                                      message
     * @throws SqlFragmentNotFoundException The name of the fragment for sql is not
     *                                      found.
     * @throws IOException                  If the forgot password file can not be
     *                                      found.
     */
    public void forgotPassword(String email) throws MessagingException, Exception {
        String content = getForgotPasswordContent(email);

        if ("".equals(content)) {
            return;
        } else {
            sendEmail(buildUserEmail(email, "Forgot Password", content), true);
        }
    }

    /**
     * This will send a report to the individual regionals of what managers are on
     * vacation. As well as admins and sitAdmins will get notifications of all users
     * that are on vacation for the given week.
     * 
     * @throws Exception
     */
    public List<User> sendVacationReport() throws Exception {
        String filePath = String.format("%s/WeeklyVacationsReport.html", BASE_HTML_PATH);
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String emailContent = br.lines().collect(Collectors.joining(" "));

        List<User> usersWithNotifications = getUsersWithNotificationsEnabled();
        for (User user : usersWithNotifications) {
            VacationRequest vRequest = new VacationRequest();
            vRequest.setStatus(Sets.newHashSet(VacationStatus.APPROVED));
            if (user.getWebRole().equals(WebRole.REGIONAL) || user.getWebRole().equals(WebRole.DISTRICT_MANAGER)) {
                vRequest.setRegionalId(Sets.newHashSet(user.getId()));
            }

            sendEmail(buildUserEmail(user.getEmail(), "Weekly Report",
                    emailContent
                            .replace("::REPLACE_CARDS::",
                                    buildHTMLCard(vcationController.getVacationsForReport(vRequest)))
                            .replace("::DATE_TODAY::", LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.SATURDAY))
                                    .format(DateTimeFormatter.ofPattern("MMMM d, yyyy")))),
                    true);
        }
        br.close();
        return usersWithNotifications;
    }

    /**
     * This method will be called when a new user creates an account. They will
     * receieve this email to update them on the status of their account and weather
     * or not it has been approved or denied.
     * 
     * @throws Exception
     */
    public void sendNewUserEmail(User newUser) throws Exception {
        String filePath = String.format("%s/NewUserEmail.html", BASE_HTML_PATH);
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String emailContent = br.lines().collect(Collectors.joining(" "));

        sendEmail(buildUserEmail(newUser.getEmail(), "Welcome to Marc's!",
                emailContent.replace("::USER_NAME::", newUser.getFirstName())), true);
        br.close();
    }

    /**
     * Email endpoint to send status update of a users account
     * 
     * @param userId The user to send the email too.
     * @param status The status of their account.
     */
    public User sendUserAccountUpdateStatusEmail(int userId, AccountStatus status) throws Exception {
        User emailUser = userClient.getUserById(userId);
        String filePath = String.format("%s/UserAccount%s.html", BASE_HTML_PATH, status.name());
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String emailContent = br.lines().collect(Collectors.joining(" "));

        sendEmail(buildUserEmail(emailUser.getEmail(), "Marc's Account Update!",
                emailContent.replace("::USER_NAME::", emailUser.getFirstName())), true);
        br.close();
        return emailUser;
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
    private UserEmail buildUserEmail(String to, String subject, String body) {
        UserEmail userEmail = new UserEmail();
        userEmail.setFrom("marcsapp@outlook.com");
        userEmail.setRecipient(to);
        userEmail.setSubject(subject);
        userEmail.setBody(body);
        return userEmail;
    }

    /**
     * This will build out the reset password link that will be sent with the email.
     * If the users email does not exist this method will return an empty string and
     * it will not send an email.
     * 
     * @param email The users email to search for and send an email too.
     * @return {@link String} of the email content with the replaced link.
     * @throws Exception
     */
    private String getForgotPasswordContent(String email) throws Exception {
        String filePath = String.format("%s/ForgotPasswordEmail.html", BASE_HTML_PATH);
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String emailContent = br.lines().collect(Collectors.joining(" "));
        br.close();

        UserGetRequest request = new UserGetRequest();
        request.setEmail(Sets.newHashSet(email));
        List<User> users = userClient.getUsers(request);

        if (users.size() < 1)
            return "";
        else
            return emailContent.replace("::FORGOT_PASSWORD_LINK::",
                    RESET_LINK + jwtTokenUtil.generateToken(users.get(0), true));
    }

    /**
     * Get the list of regionals that have notifications enabled to get a weekly
     * reminder.
     * 
     * @return List of users
     * @throws Exception
     */
    private List<User> getUsersWithNotificationsEnabled() throws Exception {
        UserGetRequest request = new UserGetRequest();
        request.setWebRole(Sets.newHashSet(WebRole.REGIONAL, WebRole.DISTRICT_MANAGER, WebRole.ADMIN));
        request.setNotificationsEnabled(true);
        return userClient.getUsers(request);
    }

    /**
     * Build out the vacation cards
     * 
     * @param vacs The vacations for the regional
     * @return Formatted String
     */
    private String buildHTMLCard(List<Vacation> vacs) {
        if (vacs.size() == 0) {
            return "No Vacations";
        }

        String htmlCards = "";
        String defaultCard = "<div class=\"card\"><div class=\"card-data\">::DATA_NAME::</div><div class=\"card-date\">::DATA_DATE::</div></div>";

        for (Vacation vac : vacs) {
            String replacementValueName = String.format("%s (%s)", vac.getFullName(), vac.getStoreId());
            String replacementValueDate = String.format("%s - %s",
                    vac.getStartDate().format(DateTimeFormatter.ofPattern("MMMM d, yyyy")),
                    vac.getEndDate().format(DateTimeFormatter.ofPattern("MMMM d, yyyy")));

            htmlCards += defaultCard.replace("::DATA_NAME::", replacementValueName).replace("::DATA_DATE::",
                    replacementValueDate);
        }
        return htmlCards;
    }

}
