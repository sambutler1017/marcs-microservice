package com.marcs.app.email.processors;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marcs.app.user.client.UserProfileClient;
import com.marcs.app.user.client.domain.User;

/**
 * Forgot Password email processor
 * 
 * @author Sam Butler
 * @since December 20, 2022
 */
@Service
public class UserAccountStatusUpadteEmailProcessor extends EmailProcessor<Integer> {
    @Autowired
    private UserProfileClient userClient;

    private int userId;

    @Override
    public void process() throws Exception {
        User emailUser = userClient.getUserById(userId);
        String filePath = String.format("%s/UserAccount%s.html", BASE_HTML_PATH, emailUser.getAccountStatus().name());
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String emailContent = br.lines().collect(Collectors.joining(" "));

        send(buildUserEmail(emailUser.getEmail(), "Marc's Account Update!",
                emailContent.replace("::USER_NAME::", emailUser.getFirstName())));
        br.close();
    }

    @Override
    public void setParams(Integer params) {
        this.userId = params;

    }
}
