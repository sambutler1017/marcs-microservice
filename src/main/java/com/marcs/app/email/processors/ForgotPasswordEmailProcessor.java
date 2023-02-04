/**
 * Copyright of Marcs App. All rights reserved.
 */
package com.marcs.app.email.processors;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Sets;
import com.marcs.app.email.client.domain.UserEmail;
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
    public List<UserEmail> process() {
        String content = getForgotPasswordContent(email);

        if(!"".equals(content)) {
            return List.of(send(buildUserEmail(email, "Forgot Password", content)));
        }
        else {
            LOGGER.warn("Email could not be processed. No user found for email '{}'", email);
        }
        return null;
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
     */
    private String getForgotPasswordContent(String email) {
        final String emailContent = readEmailTemplate("ForgotPasswordEmail.html");

        UserGetRequest request = new UserGetRequest();
        request.setEmail(Sets.newHashSet(email));
        List<User> users = userClient.getUsers(request);

        if(users.size() < 1)
            return "";
        else
            return emailContent.replace("::FORGOT_PASSWORD_LINK::",
                                        RESET_LINK + jwtTokenUtil.generateToken(users.get(0), true));
    }
}
