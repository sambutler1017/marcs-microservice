package com.marcs.app.vacation.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marcs.app.vacation.client.domain.Vacation;
import com.marcs.app.vacation.rest.VacationController;

/**
 * This class exposes the vacation endpoint's to other app's to pull data across
 * the platform.
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Component
public class VacationClient {

	@Autowired
	private VacationController vacationController;

	/**
	 * Client method to get a list of vacations by manager id
	 * 
	 * @param id of the manager to search under
	 * @return List of vacations under that regional
	 * @throws Exception
	 */
	public List<Vacation> getManagerVacations(int id) {
		return vacationController.getManagerVacations(id);
	}
}
