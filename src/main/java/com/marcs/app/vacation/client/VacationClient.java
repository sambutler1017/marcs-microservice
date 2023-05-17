/**
 * Copyright of Marcs App. All rights reserved.
 */
package com.marcs.app.vacation.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.marcs.annotations.interfaces.Client;
import com.marcs.app.vacation.client.domain.Vacation;
import com.marcs.app.vacation.client.domain.request.VacationGetRequest;
import com.marcs.app.vacation.service.ManageVacationService;
import com.marcs.app.vacation.service.VacationService;

/**
 * This class exposes the vacation endpoint's to other app's to pull data across
 * the platform.
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Client
public class VacationClient {

	@Autowired
	private VacationService vacationService;

	@Autowired
	private ManageVacationService manageVacationService;

	/**
	 * Get List of vacations for the current request.
	 * 
	 * @return {@link Vacation} object.
	 */
	public List<Vacation> getVacations(VacationGetRequest request) {
		return vacationService.getVacations(request).getList();
	}

	/**
	 * Gets the current logged in users vacations.
	 * 
	 * @return {@link Vacation} object.
	 */
	public List<Vacation> getCurrentUserVacations(VacationGetRequest request) {
		return vacationService.getCurrentUserVacations(request).getList();
	}

	/**
	 * Get a list of vacations for the given user id.
	 * 
	 * @param userId The user id to get vacations for.
	 * @return {@link Lst<Vacation>} for the user.
	 */
	public List<Vacation> getVacationsByUserId(int userId, VacationGetRequest request) {
		return vacationService.getVacationsByUserId(userId, request).getList();
	}

	/**
	 * Get the vacation for the given id of the vacation.
	 * 
	 * @param id The id of the vacation inserted.
	 * @return {@link Vacation} object.
	 */
	public Vacation getVacationById(int id) {
		return vacationService.getVacationById(id);
	}

	/**
	 * Get vacations for a report based on the current day.
	 * 
	 * @param userId The user id to get vacations for.
	 * @return {@link Lst<Vacation>} for the user.
	 */
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
	public Vacation createVacation(int id, Vacation vac) {
		return manageVacationService.createVacation(id, vac);
	}

	/**
	 * Request a vacation. This will be used by users that are asking to take a
	 * vacation.
	 * 
	 * @param vac The vacation to be inserted.
	 * @return {@link Vacation} for the user.
	 */
	public Vacation requestVacation(Vacation vac) throws Exception {
		return manageVacationService.requestVacation(vac);
	}

	/**
	 * Create multiple vacations and give them to the desired user file.
	 * 
	 * @param id   The user id to get vacations for.
	 * @param vacs The list of vacations to be inserted.
	 * @return {@link List<Vacation>} for the user.
	 */
	public List<Vacation> createBatchVacations(int id, List<Vacation> vacs) {
		return manageVacationService.createBatchVacations(id, vacs);
	}

	/**
	 * Simple helper method to mark the vacations stored as expired if the date has
	 * already passed.
	 */
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
	public Vacation updateVacationDatesById(int id, Vacation vac) {
		return manageVacationService.updateVacationDatesById(id, vac);
	}

	/**
	 * Update the values for a given vacation ID and object.
	 * 
	 * @param id  The id of the vacation inserted.
	 * @param vac What to update on for the vacation.
	 * @return {@link Vacation} object.
	 */
	public Vacation updateVacationInfoById(int id, Vacation vac) {
		return manageVacationService.updateVacationInfoById(id, vac);
	}

	/**
	 * Deletes all vacations for the currently logged in user.
	 * 
	 * @param userId The user id to get vacations for.
	 * @return {@link Lst<Vacation>} for the user.
	 */
	public void deleteAllCurrentUserVacations() {
		manageVacationService.deleteAllCurrentUserVacations();
	}

	/**
	 * Get a list of vacations for the given user id.
	 * 
	 * @param id The id for the vacation
	 * @return {@link Lst<Vacation>} for the user.
	 */
	public void deleteVacationById(int id) {
		manageVacationService.deleteVacationById(id);
	}

	/**
	 * Deletes all user vacations for the given user id.
	 * 
	 * @param userId The user id to get vacations for.
	 * @return {@link Lst<Vacation>} for the user.
	 */
	public void deleteAllVacationsByUserId(int userId) {
		manageVacationService.deleteAllVacationsByUserId(userId);
	}

	/**
	 * Deletes all expired vacations in the given range. Range will be a value in
	 * months.
	 * 
	 * @param range The range to delete in months
	 * @return How many rows were deleted
	 */
	public int deleteAllExpiredVacations(int range) {
		return manageVacationService.deleteAllExpiredVacations(range);
	}
}
