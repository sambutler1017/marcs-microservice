package com.marcs.app.vacation;

import com.marcs.app.vacation.client.VacationClient;

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
public class VacationScheduler {
    private static final Logger LOGGER = LoggerFactory.getLogger(VacationScheduler.class);

    @Autowired
    private VacationClient vacationClient;

    /**
     * Scheduler that gets run every day at 12:00 AM (Midnight) for marking
     * vacations as expired.
     * 
     * @throws Exception If the reports were not able to be sent.
     */
    @Scheduled(cron = "0 0 0 * * *", zone = "UTC")
    public void create() throws Exception {
        LOGGER.info("Running Expired Vacation Script...");
        vacationClient.markExpiredVacations();
        LOGGER.info("Vacations Marked Expired Complete!");
    }
}
