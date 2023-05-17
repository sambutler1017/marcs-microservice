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
import com.marcs.app.reports.dao.UserReportsDao;
import com.marcs.app.user.client.domain.User;
import com.marcs.app.user.client.domain.request.UserGetRequest;
import com.marcs.common.csv.CSVBuilder;
import com.marcs.jwt.utility.JwtHolder;

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

    @Autowired
    private JwtHolder jwtHolder;

    /**
     * This will generate a report for a list of users.
     * 
     * @return CSV download object
     */
    public ResponseEntity<Resource> generateUserProfileReport(UserGetRequest request) throws Exception {
        List<User> userList = dao.getUsers(userAccessRestrictions(request));

        return CSVBuilder.download(userList).withColumn("ID", "id").withColumn("First Name", "firstName")
                .withColumn("Last Name", "lastName").withColumn("Email", "email").withColumn("Web Role", "webRole")
                .withColumn("Store ID", "storeId").withColumn("Store Name", "storeName")
                .withColumn("Hire Date", "hireDate").build("user-download.csv");
    }

    /**
     * Will determine what users, the user making the request, is able to see.
     * 
     * @param r The passed in user get request.
     * @return Updated user get request.
     */
    private UserGetRequest userAccessRestrictions(UserGetRequest r) {
        if(!jwtHolder.isTokenAvaiable()) {
            return r;
        }

        if(jwtHolder.getWebRole().isAllAccessUser()) {
            // Do Nothing
        }
        else if(jwtHolder.getWebRole().isRegionalManager()) {
            r.setRegionalManagerId(Sets.newHashSet(jwtHolder.getUserId()));
        }
        else if(jwtHolder.getWebRole().isManager()) {
            r.setStoreId(Sets.newHashSet(jwtHolder.getUser().getStoreId()));
        }
        else { // Employee User
            r.setId(Sets.newHashSet(jwtHolder.getUserId()));
        }

        return r;
    }
}
