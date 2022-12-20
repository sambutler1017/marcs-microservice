package com.marcs.app.email.processors;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Sets;
import com.marcs.app.user.client.UserProfileClient;
import com.marcs.app.user.client.domain.User;
import com.marcs.app.user.client.domain.request.UserGetRequest;
import com.marcs.app.vacation.client.domain.Vacation;
import com.marcs.app.vacation.client.domain.request.VacationGetRequest;
import com.marcs.app.vacation.rest.VacationController;
import com.marcs.common.enums.VacationStatus;
import com.marcs.common.enums.WebRole;
import com.marcs.common.util.CommonUtil;

/**
 * Forgot Password email processor
 * 
 * @author Sam Butler
 * @since December 20, 2022
 */
@Service
public class VacationReportEmailProcessor extends EmailProcessor<Void> {

    @Autowired
    private UserProfileClient userClient;

    @Autowired
    private VacationController vacationController;

    @Override
    public void process() throws Exception {
        String filePath = String.format("%s/WeeklyVacationsReport.html", BASE_HTML_PATH);
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String emailContent = br.lines().collect(Collectors.joining(" "));

        List<User> usersWithNotifications = getUsersWithEmailReportsEnabled();
        for(User user : usersWithNotifications) {
            VacationGetRequest vRequest = new VacationGetRequest();
            vRequest.setStatus(Sets.newHashSet(VacationStatus.APPROVED));
            if(user.getWebRole().equals(WebRole.REGIONAL) || user.getWebRole().equals(WebRole.DISTRICT_MANAGER)) {
                vRequest.setRegionalId(Sets.newHashSet(user.getId()));
            }

            send(buildUserEmail(user.getEmail(), "Weekly Report", emailContent
                    .replace("::REPLACE_CARDS::", buildHTMLCard(vacationController.getVacationsForReport(vRequest)))
                    .replace("::DATE_TODAY::", LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.SATURDAY))
                            .format(DateTimeFormatter.ofPattern("MMMM d, yyyy")))));
        }
        br.close();
    }

    @Override
    public void setParams(Void params) {}

    /**
     * Get the list of regionals that have notifications enabled to get a weekly
     * reminder of who is on vacation.
     * 
     * @return List of users
     * @throws Exception
     */
    private List<User> getUsersWithEmailReportsEnabled() throws Exception {
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
        if(vacs.size() == 0) {
            return "No Vacations";
        }

        String htmlCards = "";
        String defaultCard = "<div class=\"card\"><div class=\"card-data\">::DATA_NAME::</div><div class=\"card-date\">::DATA_DATE::</div></div>";

        for(Vacation vac : vacs) {
            String replacementValueName = String.format("%s (%s)", vac.getFullName(), vac.getStoreId());
            String replacementValueDate = String.format("%s - %s",
                                                        CommonUtil.formatDate(vac.getStartDate(), "MMMM d, yyyy"),
                                                        CommonUtil.formatDate(vac.getEndDate(), "MMMM d, yyyy"));

            htmlCards += defaultCard.replace("::DATA_NAME::", replacementValueName).replace("::DATA_DATE::",
                                                                                            replacementValueDate);
        }
        return htmlCards;
    }
}
