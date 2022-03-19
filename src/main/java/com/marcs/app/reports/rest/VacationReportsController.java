package com.marcs.app.reports.rest;

import com.marcs.app.reports.service.VacationReportsService;
import com.marcs.app.vacation.client.domain.request.VacationGetRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/reports-app/vacation")
@RestController
public class VacationReportsController {

	@Autowired
	private VacationReportsService service;

	/**
	 * This will generate a report for a list of users.
	 * 
	 * @return CSV download object
	 * @throws Exception
	 */
	@GetMapping("/generate")
	public ResponseEntity<Resource> generateUserVacations(VacationGetRequest request) throws Exception {
		return service.generateUserProfileReport(request);
	}
}
