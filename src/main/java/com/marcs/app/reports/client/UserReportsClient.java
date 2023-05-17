/**
 * Copyright of Marcs App. All rights reserved.
 */
package com.marcs.app.reports.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import com.marcs.annotations.interfaces.Client;
import com.marcs.app.reports.service.UserReportsService;
import com.marcs.app.user.client.domain.request.UserGetRequest;

/**
 * This class exposes the user endpoint's to other app's to pull data across the
 * platform.
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Client
public class UserReportsClient {

	@Autowired
	private UserReportsService service;

	/**
	 * This will generate a report for a list of users.
	 * 
	 * @return CSV download object
	 */
	public ResponseEntity<Resource> generateUserProfileReport(UserGetRequest request) throws Exception {
		return service.generateUserProfileReport(request);
	}

}
