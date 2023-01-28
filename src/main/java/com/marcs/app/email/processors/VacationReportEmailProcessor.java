/**
 * Copyright (c) 2023 Marcs App.
 * All rights reserved.
 */
package com.marcs.app.email.processors;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Sets;
import com.marcs.app.email.client.domain.UserEmail;
import com.marcs.app.user.client.UserProfileClient;
import com.marcs.app.user.client.domain.User;
import com.marcs.app.user.client.domain.request.UserGetRequest;
import com.marcs.app.vacation.client.domain.Vacation;
import com.marcs.app.vacation.client.domain.request.VacationGetRequest;
import com.marcs.app.vacation.rest.VacationController;
import com.marcs.common.date.LocalDateFormatter;
import com.marcs.common.date.TimeZoneUtil;
import com.marcs.common.enums.VacationStatus;
import com.marcs.common.enums.WebRole;

/**
 * Forgot Password email processor
 * 
 * @author Sam Butler
 * @since December 20, 2022
 */
@Service
public class VacationReportEmailProcessor extends EmailProcessor<Void> {
    private static final String EMAIL_DYNAMIC_CARDS = "::REPLACE_CARDS::";
    private static final String EMAIL_DYNAMIC_DATE = "::DATE_TODAY::";
    private static final String EMAIL_DYNAMIC_DATA_NAME = "::DATA_NAME::";
    private static final String EMAIL_DYNAMIC_DATA_DATE = "::DATA_DATE::";

    @Autowired
    private UserProfileClient userClient;

    @Autowired
    private VacationController vacationController;

    @Override
    public List<UserEmail> process() {
        String emailContent = readEmailTemplate("WeeklyVacationsReport.html");
        List<User> usersWithNotifications = getUsersWithEmailReportsEnabled();
        List<UserEmail> emails = new ArrayList<>();

        for (User user : usersWithNotifications) {
            VacationGetRequest req = new VacationGetRequest();
            req.setStatus(Sets.newHashSet(VacationStatus.APPROVED));
            if (user.getWebRole().equals(WebRole.REGIONAL) || user.getWebRole().equals(WebRole.DISTRICT_MANAGER)) {
                req.setRegionalId(Sets.newHashSet(user.getId()));
            }

            emails.add(send(user.getEmail(), "Weekly Report", buildEmailBody(emailContent, req)));
        }
        return emails;
    }

    @Override
    public void setParams(Void params) {
    }

    /**
     * Helper method for replacing the dynamic fields in the email message.
     * 
     * @param content The content to replace with.
     * @param req     The vacation get Request for the email cards.
     * @return String of the email content.
     */
    private String buildEmailBody(String content, VacationGetRequest req) {
        String vacationCards = buildHTMLCard(vacationController.getVacationsForReport(req));
        LocalDate dt = LocalDate.now(TimeZoneUtil.SYSTEM_ZONE).with(TemporalAdjusters.next(DayOfWeek.SATURDAY));
        content = content.replace(EMAIL_DYNAMIC_CARDS, vacationCards);
        content = content.replace(EMAIL_DYNAMIC_DATE, formatDate(dt));
        return content;
    }

    /**
     * Get the list of regionals that have notifications enabled to get a weekly
     * reminder of who is on vacation.
     * 
     * @return List of users
     * @throws Exception
     */
    private List<User> getUsersWithEmailReportsEnabled() {
        UserGetRequest request = new UserGetRequest();
        request.setWebRole(Sets.newHashSet(WebRole.REGIONAL, WebRole.DISTRICT_MANAGER, WebRole.ADMIN));
        request.setEmailReportsEnabled(true);
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
            String replaceName = String.format("%s (%s)", vac.getFullName(), vac.getStoreId());
            String replaceDate = String.format("%s - %s", formatDate(vac.getStartDate()), formatDate(vac.getEndDate()));
            htmlCards += defaultCard.replace(EMAIL_DYNAMIC_DATA_NAME, replaceName).replace(EMAIL_DYNAMIC_DATA_DATE,
                    replaceDate);
        }
        return htmlCards;
    }

    /**
     * Formts the local date to a user readable format.
     * 
     * @param dt The date to format.
     * @return The string representation of the formatted date.
     */
    private String formatDate(LocalDate dt) {
        return LocalDateFormatter.formatDate(dt, "MMMM d, yyyy");
    }
}
