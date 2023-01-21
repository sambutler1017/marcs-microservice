package com.marcs.app.email.processors;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Sets;
import com.marcs.app.email.client.domain.DynamicTemplatePersonalization;
import com.marcs.app.user.client.UserProfileClient;
import com.marcs.app.user.client.domain.User;
import com.marcs.app.user.client.domain.request.UserGetRequest;
import com.marcs.jwt.utility.JwtTokenUtil;

/**
 * Forgot Password email processor
 * 
 * @author Sam Butler
 * @since December 20, 2022
 */
@Service
public class ForgotPasswordEmailProcessor extends EmailProcessor<String> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ForgotPasswordEmailProcessor.class);
    private final String RESET_LINK = "https://marcs-app.herokuapp.com/login/reset-password/";

    @Autowired
    private UserProfileClient userClient;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private String email;

    @Override
    public void process() throws Exception {
        String content = getForgotPasswordContent(email);

        if (!"".equals(content)) {
            send(buildUserEmail(email, "Forgot Password", content));
        } else {
            LOGGER.warn("Email could not be processed. No user found for email '{}'", email);
        }
    }

    @Override
    public DynamicTemplatePersonalization generatePersonalization() {
        final DynamicTemplatePersonalization personalization = new DynamicTemplatePersonalization();
        personalization.addTo(null);
        return personalization;
    }

    @Override
    public void setParams(String email) {
        this.email = email;
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
}
