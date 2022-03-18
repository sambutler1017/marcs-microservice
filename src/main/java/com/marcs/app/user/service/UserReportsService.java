package com.marcs.app.user.service;

import java.util.List;

import com.marcs.app.user.client.domain.User;
import com.marcs.app.user.client.domain.request.UserGetRequest;
import com.marcs.app.user.dao.UserReportsDao;
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
public class UserReportsService {

    @Autowired
    private UserReportsDao dao;

    /**
     * This will generate a report for a list of users.
     * 
     * @return CSV download object
     * @throws Exception
     */
    public ResponseEntity<Resource> generateUserProfileReport(UserGetRequest request) throws Exception {
        List<User> userList = dao.getUsers(request);

        return CSVBuilder.download(userList).withColumn("ID", "id").withColumn("First Name", "firstName")
                .withColumn("Last Name", "lastName").withColumn("Email", "email").withColumn("Web Role", "webRole")
                .withColumn("Store ID", "storeId").withColumn("Store Name", "storeName")
                .withColumn("Hire Date", "hireDate")
                .build("user-download.csv");
    }
}
