package com.marcs.app.reports.service;

import java.util.List;

import com.marcs.app.reports.dao.VacationReportsDao;
import com.marcs.app.vacation.client.domain.Vacation;
import com.marcs.app.vacation.client.domain.request.VacationGetRequest;
import com.marcs.common.csv.CSVBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

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

    /**
     * This will generate a report for a list of vacations.
     * 
     * @return CSV download object
     * @throws Exception
     */
    public ResponseEntity<Resource> generateUserProfileReport(VacationGetRequest request) throws Exception {
        List<Vacation> vacationList = dao.getVacations(request);

        return CSVBuilder.download(vacationList).withColumn("User ID", "userId").withColumn("Name", "fullName")
                .withColumn("Store ID", "storeId").withColumn("Start Date", "startDate")
                .withColumn("End Date", "endDate")
                .withColumn("Notes", "notes").withColumn("Date Created", "insertDate")
                .withColumn("Status", "status")
                .build("vacation-download.csv");
    }
}
