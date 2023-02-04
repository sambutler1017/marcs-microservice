/**
 * Copyright of Marcs App. All rights reserved.
 */
package com.marcs.app.vacation.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.marcs.annotations.interfaces.Client;
import com.marcs.app.vacation.client.domain.Vacation;
import com.marcs.app.vacation.client.domain.request.VacationGetRequest;
import com.marcs.app.vacation.rest.VacationController;

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
	private VacationController controller;

	/**
	 * Get list of vacations for the current request.
	 * 
	 * @return {@link Vacation} object.
	 */
	public List<Vacation> getVacations(VacationGetRequest request) throws Exception {
		return controller.getVacations(request);
	}

	/**
	 * Client method to get a list of vacations by user id
	 * 
	 * @param id of the user to search under
	 * @return List of vacations under that regional
	 */
	public List<Vacation> getVacationsByUserId(int id) throws Exception {
		return controller.getVacationsByUserId(id);
	}

	/**
	 * Simple helper method to mark the vacations stored as expired if the date has
	 * already passed.
	 */
	public int markExpiredVacations() {
		return controller.markExpiredVacations();
	}

	/**
	 * Deletes all expired vacations in the given range. Range will be a value in
	 * months.
	 * 
	 * @param range The range to delete in months
	 * @return How many rows were deleted
	 */
	public int deleteAllExpiredVacations(int range) {
		return controller.deleteAllExpiredVacations(range);
	}
}
