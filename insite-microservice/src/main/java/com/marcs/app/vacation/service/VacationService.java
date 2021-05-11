package com.marcs.app.vacation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marcs.app.vacation.client.domain.Vacation;
import com.marcs.app.vacation.dao.VacationDAO;

/**
 * Vacation Service class that handles all service calls to the dao
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Component
public class VacationService {

	@Autowired
	private VacationDAO vacationDao;

	/**
	 * Service to get a list of vacations for the given manager id
	 * 
	 * @param id of the manager
	 * @return list of vacations {@link Vacation}
	 */
	public List<Vacation> getVacationsByManagerId(int id) {
		return vacationDao.getVacationsByManagerId(id);
	}
}
