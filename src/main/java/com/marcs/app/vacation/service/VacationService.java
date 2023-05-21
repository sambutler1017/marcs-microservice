/**
 * Copyright of Marcs App. All rights reserved.
 */
package com.marcs.app.vacation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Sets;
import com.marcs.app.vacation.client.domain.Vacation;
import com.marcs.app.vacation.client.domain.request.VacationGetRequest;
import com.marcs.app.vacation.dao.VacationDao;
import com.marcs.common.enums.WebRole;
import com.marcs.common.page.Page;
import com.marcs.exceptions.type.VacationNotFoundException;
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
	 * Get page of vacations for the current request.
	 * 
	 * @param request The vacation request filters
	 * @return {@link Vacation} object.
	 */
	public Page<Vacation> getVacations(VacationGetRequest request) {
		return dao.getVacations(vacationAccessRestrictions(request));
	}

	/**
	 * Gets the current logged in users vacations.
	 * 
	 * @param request The vacation request filters
	 * @return {@link Vacation} object.
	 */
	public Page<Vacation> getCurrentUserVacations(VacationGetRequest request) {
		return getVacationsByUserId(jwtHolder.getUserId(), request);
	}

	/**
	 * Get a list of vacations for the given user id.
	 * 
	 * @param userId The user id to get vacations for.
	 * @return {@link List<Vacation>} for the user.
	 */
	public Page<Vacation> getVacationsByUserId(int userId) {
		return getVacationsByUserId(userId, new VacationGetRequest());
	}

	/**
	 * Get a list of vacations for the given user id.
	 * 
	 * @param userId  The user id to get vacations for.
	 * @param request The vacation request filters
	 * @return {@link List<Vacation>} for the user.
	 */
	public Page<Vacation> getVacationsByUserId(int userId, VacationGetRequest request) {
		request.setUserId(Sets.newHashSet(userId));
		return getVacations(request);
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
		List<Vacation> vacationList = getVacations(request).getList();

		if(vacationList.isEmpty()) {
			throw new VacationNotFoundException("Vacation not found for id: " + id);
		}
		return vacationList.get(0);
	}

	/**
	 * Get vacations for a report based on the current day.
	 * 
	 * @return {@link List<Vacation>} for the user.
	 */
	public List<Vacation> getVacationsForReport(VacationGetRequest request) {
		return dao.getVacationsForReport(vacationAccessRestrictions(request));
	}

	/**
	 * Will determine what vacations, the user making the request, is able to see.
	 * 
	 * @param r The passed in vacation get request.
	 * @return Updated vacation get request.
	 */
	private VacationGetRequest vacationAccessRestrictions(VacationGetRequest r) {
		if(!jwtHolder.isTokenAvaiable()) {
			return r;
		}

		if(jwtHolder.getWebRole().isAllAccessUser()) {
			// Can See all vacations
		}
		else if(jwtHolder.getWebRole().isRegionalManager()) {
			r.setRegionalManagerId(Sets.newHashSet(jwtHolder.getUserId()));
		}
		else if(jwtHolder.getWebRole().isManager() || jwtHolder.getWebRole().equals(WebRole.EMPLOYEE)) {
			r.setStoreId(Sets.newHashSet(jwtHolder.getUser().getStoreId()));
		}
		else {// No Access
			r.setStoreId(Sets.newHashSet("NO_ACCESS"));
		}

		return r;
	}
}
