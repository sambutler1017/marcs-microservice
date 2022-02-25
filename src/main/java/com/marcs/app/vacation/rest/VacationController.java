package com.marcs.app.vacation.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import com.marcs.annotations.interfaces.RestApiController;
import com.marcs.app.vacation.client.domain.Vacation;
import com.marcs.app.vacation.client.domain.request.VacationRequest;
import com.marcs.app.vacation.service.VacationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("api/vacation-app/vacations")
@RestApiController
public class VacationController {

	@Autowired
	private VacationService service;

	/**
	 * Get list of vacations for the current request.
	 * 
	 * @return {@link Vacation} object.
	 * @throws Exception
	 */
	@GetMapping(produces = APPLICATION_JSON_VALUE)
	public List<Vacation> getVacations(VacationRequest request) throws Exception {
		return service.getVacations(request);
	}

	/**
	 * Gets the current logged in users vacations.
	 * 
	 * @return {@link Vacation} object.
	 * @throws Exception
	 */
	@GetMapping(path = "/current-user", produces = APPLICATION_JSON_VALUE)
	public List<Vacation> getCurrentUserVacations() throws Exception {
		return service.getCurrentUserVacations();
	}

	/**
	 * Get the vacation for the given id of the vacation.
	 * 
	 * @param id The id of the vacation inserted.
	 * @return {@link Vacation} object.
	 * @throws Exception
	 */
	@GetMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
	public Vacation getVacationById(@PathVariable int id) throws Exception {
		return service.getVacationById(id);
	}

	/**
	 * Get a list of vacations for the given user id.
	 * 
	 * @param userId The user id to get vacations for.
	 * @return {@link Lst<Vacation>} for the user.
	 * @throws Exception
	 */
	@GetMapping(path = "/{userId}/user", produces = APPLICATION_JSON_VALUE)
	public List<Vacation> getVacationsByUserId(@PathVariable int userId) throws Exception {
		return service.getVacationsByUserId(userId);
	}

	/**
	 * Get vacations for a report based on the current day.
	 * 
	 * @param userId The user id to get vacations for.
	 * @return {@link Lst<Vacation>} for the user.
	 * @throws Exception
	 */
	@GetMapping(path = "/report", produces = APPLICATION_JSON_VALUE)
	public List<Vacation> getVacationsForReport(VacationRequest request) throws Exception {
		return service.getVacationsForReport(request);
	}

	/**
	 * Create a vacation for the given user.
	 * 
	 * @param id  The user id to get vacations for.
	 * @param vac The vacation to be inserted.
	 * @return {@link Vacation} for the user.
	 * @throws Exception
	 */
	@PostMapping(path = "/{id}/user", produces = APPLICATION_JSON_VALUE)
	public Vacation createVacation(@PathVariable int id, @RequestBody Vacation vac) throws Exception {
		return service.createVacation(id, vac);
	}

	/**
	 * Request a vacation. This will be used by users that are asking to take a
	 * vacation.
	 * 
	 * @param vac The vacation to be inserted.
	 * @return {@link Vacation} for the user.
	 * @throws Exception
	 */
	@PostMapping(path = "/request", produces = APPLICATION_JSON_VALUE)
	public Vacation requestVacation(@RequestBody Vacation vac) throws Exception {
		return service.requestVacation(vac);
	}

	/**
	 * Create multiple vacations and give them to the desired user file.
	 * 
	 * @param id   The user id to get vacations for.
	 * @param vacs The list of vacations to be inserted.
	 * @return {@link List<Vacation>} for the user.
	 * @throws Exception
	 */
	@PostMapping(path = "/{id}/user/batch", produces = APPLICATION_JSON_VALUE)
	public List<Vacation> createBatchVacations(@PathVariable int id, @RequestBody List<Vacation> vacs)
			throws Exception {
		return service.createBatchVacations(id, vacs);
	}

	/**
	 * Simple helper method to mark the vacations stored as expired if the date has
	 * already passed.
	 */
	@PostMapping("/mark-expired")
	public void markExpiredVacations() {
		service.markExpiredVacations();
	}

	/**
	 * This will update the vacations dates for the given vacation id.
	 * 
	 * @param id  The id of the vacation to be updates.
	 * @param vac What to update the vacation dates too.
	 * @return Vacation object of the updated information.
	 * @throws Exception
	 */
	@PutMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
	public Vacation updateVacationDatesById(@PathVariable int id, @RequestBody Vacation vac) throws Exception {
		return service.updateVacationDatesById(id, vac);
	}

	/**
	 * Update the values for a given vacation ID and object.
	 * 
	 * @param id  The id of the vacation inserted.
	 * @param vac What to update on for the vacation.
	 * @return {@link Vacation} object.
	 * @throws Exception
	 */
	@PutMapping(path = "/{id}/info", produces = APPLICATION_JSON_VALUE)
	public Vacation updateVacationInfoById(@PathVariable int id, @RequestBody Vacation vac) throws Exception {
		return service.updateVacationInfoById(id, vac);
	}

	/**
	 * Deletes all vacations for the currently logged in user.
	 * 
	 * @param userId The user id to get vacations for.
	 * @return {@link Lst<Vacation>} for the user.
	 * @throws Exception
	 */
	@DeleteMapping()
	public void deleteAllCurrentUserVacations() throws Exception {
		service.deleteAllCurrentUserVacations();
	}

	/**
	 * Get a list of vacations for the given user id.
	 * 
	 * @param id The id for the vacation
	 * @return {@link Lst<Vacation>} for the user.
	 * @throws Exception
	 */
	@DeleteMapping("/{id}")
	public void deleteVacationById(@PathVariable int id) throws Exception {
		service.deleteVacationById(id);
	}

	/**
	 * Deletes all user vacations for the given user id.
	 * 
	 * @param userId The user id to get vacations for.
	 * @return {@link Lst<Vacation>} for the user.
	 * @throws Exception
	 */
	@DeleteMapping("/{userId}/user")
	public void deleteAllVacationsByUserId(@PathVariable int userId) throws Exception {
		service.deleteAllVacationsByUserId(userId);
	}
}