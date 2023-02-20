/**
 * Copyright of Marcs App. All rights reserved.
 */
package com.marcs.app.vacation.rest;

import static org.springframework.http.MediaType.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.marcs.annotations.interfaces.RestApiController;
import com.marcs.app.vacation.client.domain.Vacation;
import com.marcs.app.vacation.client.domain.request.VacationGetRequest;
import com.marcs.app.vacation.service.ManageVacationService;
import com.marcs.app.vacation.service.VacationService;
import com.marcs.common.page.Page;

@RequestMapping("api/vacation-app/vacations")
@RestApiController
public class VacationController {

	@Autowired
	private VacationService vacationService;

	@Autowired
	private ManageVacationService manageVacationService;

	/**
	 * Get page of vacations for the current request.
	 * 
	 * @return {@link Vacation} object.
	 */
	@GetMapping(produces = APPLICATION_JSON_VALUE)
	public Page<Vacation> getVacations(VacationGetRequest request) {
		return vacationService.getVacations(request);
	}

	/**
	 * Gets the current logged in users vacations.
	 * 
	 * @return {@link Vacation} object.
	 */
	@GetMapping(path = "/current-user", produces = APPLICATION_JSON_VALUE)
	public List<Vacation> getCurrentUserVacations() {
		return vacationService.getCurrentUserVacations();
	}

	/**
	 * Get the vacation for the given id of the vacation.
	 * 
	 * @param id The id of the vacation inserted.
	 * @return {@link Vacation} object.
	 */
	@GetMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
	public Vacation getVacationById(@PathVariable int id) {
		return vacationService.getVacationById(id);
	}

	/**
	 * Get a list of vacations for the given user id.
	 * 
	 * @param userId The user id to get vacations for.
	 * @return {@link Lst<Vacation>} for the user.
	 */
	@GetMapping(path = "/{userId}/user", produces = APPLICATION_JSON_VALUE)
	public List<Vacation> getVacationsByUserId(@PathVariable int userId) {
		return vacationService.getVacationsByUserId(userId);
	}

	/**
	 * Get vacations for a report based on the current day.
	 * 
	 * @param userId The user id to get vacations for.
	 * @return {@link Lst<Vacation>} for the user.
	 */
	@GetMapping(path = "/report", produces = APPLICATION_JSON_VALUE)
	public List<Vacation> getVacationsForReport(VacationGetRequest request) {
		return vacationService.getVacationsForReport(request);
	}

	/**
	 * Create a vacation for the given user.
	 * 
	 * @param id  The user id to get vacations for.
	 * @param vac The vacation to be inserted.
	 * @return {@link Vacation} for the user.
	 */
	@PostMapping(path = "/{id}/user", produces = APPLICATION_JSON_VALUE)
	public Vacation createVacation(@PathVariable int id, @RequestBody Vacation vac) {
		return manageVacationService.createVacation(id, vac);
	}

	/**
	 * Request a vacation. This will be used by users that are asking to take a
	 * vacation.
	 * 
	 * @param vac The vacation to be inserted.
	 * @return {@link Vacation} for the user.
	 */
	@PostMapping(path = "/request", produces = APPLICATION_JSON_VALUE)
	public Vacation requestVacation(@RequestBody Vacation vac) throws Exception {
		return manageVacationService.requestVacation(vac);
	}

	/**
	 * Create multiple vacations and give them to the desired user file.
	 * 
	 * @param id   The user id to get vacations for.
	 * @param vacs The list of vacations to be inserted.
	 * @return {@link List<Vacation>} for the user.
	 */
	@PostMapping(path = "/{id}/user/batch", produces = APPLICATION_JSON_VALUE)
	public List<Vacation> createBatchVacations(@PathVariable int id, @RequestBody List<Vacation> vacs)
			throws Exception {
		return manageVacationService.createBatchVacations(id, vacs);
	}

	/**
	 * Simple helper method to mark the vacations stored as expired if the date has
	 * already passed.
	 */
	@PostMapping("/mark-expired")
	public int markExpiredVacations() {
		return manageVacationService.markExpiredVacations();
	}

	/**
	 * This will update the vacations dates for the given vacation id.
	 * 
	 * @param id  The id of the vacation to be updates.
	 * @param vac What to update the vacation dates too.
	 * @return Vacation object of the updated information.
	 */
	@PutMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
	public Vacation updateVacationDatesById(@PathVariable int id, @RequestBody Vacation vac) {
		return manageVacationService.updateVacationDatesById(id, vac);
	}

	/**
	 * Update the values for a given vacation ID and object.
	 * 
	 * @param id  The id of the vacation inserted.
	 * @param vac What to update on for the vacation.
	 * @return {@link Vacation} object.
	 */
	@PutMapping(path = "/{id}/info", produces = APPLICATION_JSON_VALUE)
	public Vacation updateVacationInfoById(@PathVariable int id, @RequestBody Vacation vac) {
		return manageVacationService.updateVacationInfoById(id, vac);
	}

	/**
	 * Deletes all vacations for the currently logged in user.
	 * 
	 * @param userId The user id to get vacations for.
	 * @return {@link Lst<Vacation>} for the user.
	 */
	@DeleteMapping()
	public void deleteAllCurrentUserVacations() {
		manageVacationService.deleteAllCurrentUserVacations();
	}

	/**
	 * Get a list of vacations for the given user id.
	 * 
	 * @param id The id for the vacation
	 * @return {@link Lst<Vacation>} for the user.
	 */
	@DeleteMapping("/{id}")
	public void deleteVacationById(@PathVariable int id) {
		manageVacationService.deleteVacationById(id);
	}

	/**
	 * Deletes all user vacations for the given user id.
	 * 
	 * @param userId The user id to get vacations for.
	 * @return {@link Lst<Vacation>} for the user.
	 */
	@DeleteMapping("/{userId}/user")
	public void deleteAllVacationsByUserId(@PathVariable int userId) {
		manageVacationService.deleteAllVacationsByUserId(userId);
	}

	/**
	 * Deletes all expired vacations in the given range. Range will be a value in
	 * months.
	 * 
	 * @param range The range to delete in months
	 * @return How many rows were deleted
	 */
	@DeleteMapping("/expired/{range}")
	public int deleteAllExpiredVacations(@PathVariable int range) {
		return manageVacationService.deleteAllExpiredVacations(range);
	}
}