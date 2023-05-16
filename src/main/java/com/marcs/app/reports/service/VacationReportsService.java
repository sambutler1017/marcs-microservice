/**
 * Copyright of Marcs App. All rights reserved.
 */
package com.marcs.app.reports.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.google.common.collect.Sets;
import com.marcs.app.reports.dao.VacationReportsDao;
import com.marcs.app.vacation.client.domain.Vacation;
import com.marcs.app.vacation.client.domain.request.VacationGetRequest;
import com.marcs.common.csv.CSVBuilder;
import com.marcs.common.enums.WebRole;
import com.marcs.jwt.utility.JwtHolder;

/**
 * User Reports Service class that will handle all the calls for formatting a
 * csv.
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Component
public class VacationReportsService {

    @Autowired
    private VacationReportsDao dao;

    @Autowired
    private JwtHolder jwtHolder;

    /**
     * This will generate a report for a list of vacations.
     * 
     * @return CSV download object
     */
    public ResponseEntity<Resource> generateUserProfileReport(VacationGetRequest request) throws Exception {
        List<Vacation> vacationList = dao.getVacations(vacationAccessRestrictions(request));

        return CSVBuilder.download(vacationList).withColumn("User ID", "userId").withColumn("Name", "fullName")
                .withColumn("Store ID", "storeId").withColumn("Start Date", "startDate")
                .withColumn("End Date", "endDate").withColumn("Notes", "notes").withColumn("Date Created", "insertDate")
                .withColumn("Status", "status").build("vacation-download.csv");
    }

    /**
     * Will determine what vacations, the user making the request, is able to see.
     * 
     * @param r The passed in vacation get request.
     * @return Updated vacation get request.
     */
    private VacationGetRequest vacationAccessRestrictions(VacationGetRequest r) {
        if(!jwtHolder.isTokenAvaiable()) {
            return r;
        }

        if(jwtHolder.getWebRole().isAllAccessUser()) {
            // Can See all vacations
        }
        else if(jwtHolder.getWebRole().isRegional()) {
            r.setRegionalId(Sets.newHashSet(jwtHolder.getUserId()));
        }
        else if(jwtHolder.getWebRole().isManager() || jwtHolder.getWebRole().equals(WebRole.EMPLOYEE)) {
            r.setStoreId(Sets.newHashSet(jwtHolder.getUser().getStoreId()));
        }
        else {// No Access
            r.setStoreId(Sets.newHashSet("NO_ACCESS"));
        }

        return r;
    }
}
