/**
 * Copyright (c) 2023 Marcs App.
 * All rights reserved.
 */
package com.marcs.app.email.processors;

import java.util.List;

import org.springframework.stereotype.Service;

import com.marcs.app.email.client.domain.UserEmail;
import com.marcs.app.user.client.domain.User;

/**
 * Forgot Password email processor
 * 
 * @author Sam Butler
 * @since December 20, 2022
 */
@Service
public class NewUserEmailProcessor extends EmailProcessor<User> {

    private User newUser;

    @Override
    public List<UserEmail> process() {
        String emailContent = readEmailTemplate("NewUserEmail.html");

        return List.of(send(buildUserEmail(newUser.getEmail(), "Welcome to Marc's!",
                emailContent.replace("::USER_NAME::", newUser.getFirstName()))));
    }

    @Override
    public void setParams(User params) {
        this.newUser = params;

    }
}
