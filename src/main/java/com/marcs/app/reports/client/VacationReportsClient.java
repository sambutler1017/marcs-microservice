/**
 * Copyright of Marcs App. All rights reserved.
 */
package com.marcs.app.reports.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import com.marcs.annotations.interfaces.Client;
import com.marcs.app.reports.service.VacationReportsService;
import com.marcs.app.vacation.client.domain.request.VacationGetRequest;

/**
 * This class exposes the user endpoint's to other app's to pull data across the
 * platform.
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Client
public class VacationReportsClient {

	@Autowired
	private VacationReportsService service;

	/**
	 * This will generate a report for a list of vacations.
	 * 
	 * @return CSV download object
	 */
	public ResponseEntity<Resource> generateUserProfileReport(VacationGetRequest request) throws Exception {
		return service.generateUserProfileReport(request);
	}

}
