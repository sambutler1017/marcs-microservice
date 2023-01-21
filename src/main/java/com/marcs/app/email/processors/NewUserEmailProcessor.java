package com.marcs.app.email.processors;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.marcs.app.email.client.domain.DynamicTemplatePersonalization;
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
    public void process() throws Exception {
        String filePath = String.format("%s/NewUserEmail.html", BASE_HTML_PATH);
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String emailContent = br.lines().collect(Collectors.joining(" "));

        send(buildUserEmail(newUser.getEmail(), "Welcome to Marc's!",
                emailContent.replace("::USER_NAME::", newUser.getFirstName())));
        br.close();
    }

    @Override
    public DynamicTemplatePersonalization generatePersonalization() {
        final DynamicTemplatePersonalization personalization = new DynamicTemplatePersonalization();
        personalization.addTo(null);
        return personalization;
    }

    @Override
    public void setParams(User params) {
        this.newUser = params;

    }
}
