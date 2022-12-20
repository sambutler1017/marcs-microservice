package com.marcs.app.email.processors;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Sets;
import com.marcs.app.user.client.UserProfileClient;
import com.marcs.app.user.client.domain.User;
import com.marcs.app.user.client.domain.request.UserGetRequest;
import com.marcs.common.enums.WebRole;

/**
 * Forgot Password email processor
 * 
 * @author Sam Butler
 * @since December 20, 2022
 */
@Service
public class ContactAdminEmailProcessor extends EmailProcessor<String> {

    @Autowired
    private UserProfileClient userClient;

    private String email;

    @Override
    public void process() throws Exception {
        User emailUser = userClient.getCurrentUser();
        String filePath = String.format("%s/ContactAdminEmail.html", BASE_HTML_PATH);
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String emailContent = br.lines().collect(Collectors.joining(" "));
        br.close();

        UserGetRequest request = new UserGetRequest();
        request.setWebRole(Sets.newHashSet(WebRole.ADMIN));
        List<User> adminUsers = userClient.getUsers(request);

        for(User user : adminUsers) {
            send(buildUserEmail(user.getEmail(), "New Message", emailContent
                    .replace("::USER_NAME::",
                             String.format("%s %s (%s)", emailUser.getFirstName().trim(),
                                           emailUser.getLastName().trim(), emailUser.getWebRole().toString()))
                    .replace("::EMAIL_BODY::", email)));
        }
    }

    @Override
    public void setParams(String params) {
        this.email = params;
    }
}
