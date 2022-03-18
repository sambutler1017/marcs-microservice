package com.marcs.app.user.rest;

import com.marcs.app.user.client.domain.request.UserGetRequest;
import com.marcs.app.user.service.UserReportsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/user-app/user-reports")
@RestController
public class UserReportsController {

	@Autowired
	private UserReportsService service;

	/**
	 * This will generate a report for a list of users.
	 * 
	 * @return CSV download object
	 * @throws Exception
	 */
	@GetMapping("/generate")
	public ResponseEntity<Resource> generateUserProfileReport(UserGetRequest request) throws Exception {
		return service.generateUserProfileReport(request);
	}
}
