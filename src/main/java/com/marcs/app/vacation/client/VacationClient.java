package com.marcs.app.vacation.client;

import java.util.List;

import com.marcs.annotations.interfaces.Client;
import com.marcs.app.vacation.client.domain.Vacation;
import com.marcs.app.vacation.client.domain.request.VacationRequest;
import com.marcs.app.vacation.rest.VacationController;

import org.springframework.beans.factory.annotation.Autowired;

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
	 * @throws Exception
	 */
	public List<Vacation> getVacations(VacationRequest request) throws Exception {
		return controller.getVacations(request);
	}

	/**
	 * Client method to get a list of vacations by user id
	 * 
	 * @param id of the user to search under
	 * @return List of vacations under that regional
	 * @throws Exception
	 */
	public List<Vacation> getVacationsByUserId(int id) throws Exception {
		return controller.getVacationsByUserId(id);
	}
}
