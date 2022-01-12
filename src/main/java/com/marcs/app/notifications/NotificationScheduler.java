package com.marcs.app.notifications;

import com.marcs.app.email.rest.EmailController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Class that handles notifications and emails to user accounts.
 * 
 * @author Sam Butler
 * @since September 21, 2021
 */
@Component
public class NotificationScheduler {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationScheduler.class);

    @Autowired
    private EmailController emailController;

    @Scheduled(cron = "0 0 13 * * MON", zone = "UTC")
    public void create() throws Exception {
        LOGGER.info("Sending Vacation Reports...");
        emailController.sendVacationReport();
        LOGGER.info("Reports Sent Complete!");
    }
}
