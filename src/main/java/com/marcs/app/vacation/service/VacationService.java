package com.marcs.app.vacation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Sets;
import com.marcs.app.vacation.client.domain.Vacation;
import com.marcs.app.vacation.client.domain.request.VacationGetRequest;
import com.marcs.app.vacation.dao.VacationDao;
import com.marcs.jwt.utility.JwtHolder;

/**
 * Vacation Service class that handles all service calls to the dao
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Component
public class VacationService {

	@Autowired
	private VacationDao dao;

	@Autowired
	private JwtHolder jwtHolder;

	/**
	 * Get list of vacations for the current request.
	 * 
	 * @return {@link Vacation} object.
	 */
	public List<Vacation> getVacations(VacationGetRequest request) {
		return dao.getVacations(request);
	}

	/**
	 * Gets the current logged in users vacations.
	 * 
	 * @return {@link Vacation} object.
	 */
	public List<Vacation> getCurrentUserVacations() {
		return getVacationsByUserId(jwtHolder.getUserId());
	}

	/**
	 * Get the vacation for the given id of the vacation.
	 * 
	 * @param id The id of the vacation inserted.
	 * @return {@link Vacation} object.
	 */
	public Vacation getVacationById(int id) {
		VacationGetRequest request = new VacationGetRequest();
		request.setId(Sets.newHashSet(id));
		return getVacations(request).get(0);
	}

	/**
	 * Get a list of vacations for the given user id.
	 * 
	 * @param userId The user id to get vacations for.
	 * @return {@link List<Vacation>} for the user.
	 */
	public List<Vacation> getVacationsByUserId(int userId) {
		VacationGetRequest request = new VacationGetRequest();
		request.setUserId(Sets.newHashSet(userId));
		return getVacations(request);
	}

	/**
	 * Get vacations for a report based on the current day.
	 * 
	 * @return {@link List<Vacation>} for the user.
	 */
	public List<Vacation> getVacationsForReport(VacationGetRequest request) {
		return dao.getVacationsForReport(request);
	}
}
