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
	 * Get vacations for a report based on the current day.
	 * 
	 * @param userId The user id to get vacations for.
	 * @return {@link Lst<Vacation>} for the user.
	 */
	public List<Vacation> getVacationsForReport(VacationGetRequest request) {
		return vacationService.getVacationsForReport(request);
	}

	/**
	 * Simple helper method to mark the vacations stored as expired if the date has
	 * already passed.
	 */
	public int markExpiredVacations() {
		return manageVacationService.markExpiredVacations();
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
