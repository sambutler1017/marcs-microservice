/**
 * Copyright of Marcs App. All rights reserved.
 */
package com.marcs.app.reports.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marcs.app.reports.service.UserReportsService;
import com.marcs.app.user.client.domain.request.UserGetRequest;

@RequestMapping("/api/reports/user")
@RestController
public class UserReportsController {

	@Autowired
	private UserReportsService service;

	/**
	 * This will generate a report for a list of users.
	 * 
	 * @return CSV download object
	 */
	@GetMapping("/generate")
	public ResponseEntity<Resource> generateUserProfileReport(UserGetRequest request) throws Exception {
		return service.generateUserProfileReport(request);
	}
}
