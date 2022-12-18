package com.marcs.app.vacation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.marcs.app.vacation.client.VacationClient;

/**
 * Class that handles notifications and emails to user accounts.
 * 
 * @author Sam Butler
 * @since September 21, 2021
 */
@Component
public class VacationScheduler {
    private static final Logger LOGGER = LoggerFactory.getLogger(VacationScheduler.class);
    private static final int DELETE_RANGE = 6;

    @Autowired
    private VacationClient vacationClient;

    /**
     * Scheduler that gets run every Monday at 3:00 AM (UTC) for marking vacations
     * as expired.
     * 
     * @throws Exception If the reports were not able to be sent.
     */
    @Scheduled(cron = "0 0 7 * * MON", zone = "UTC")
    public void markExpiredVacations() throws Exception {
        LOGGER.info("Running Expired Vacation Script...");
        int affectedRows = vacationClient.markExpiredVacations();
        LOGGER.info("Vacations Marked Expired Complete! '{}' rows updated.", affectedRows);
    }

    /**
     * Scheduler that gets run every Monday at 5:00 AM (UTC) for marking vacations
     * as expired.
     * 
     * @throws Exception If the reports were not able to be sent.
     */
    @Scheduled(cron = "0 0 9 * * MON", zone = "UTC")
    public void removeExpiredVacations() throws Exception {
        LOGGER.info("Removing Expired Vacations...");
        int affectedRows = vacationClient.deleteAllExpiredVacations(DELETE_RANGE);
        LOGGER.info("Vacations Marked Expired Complete! '{}' rows updated.", affectedRows);
    }

}
