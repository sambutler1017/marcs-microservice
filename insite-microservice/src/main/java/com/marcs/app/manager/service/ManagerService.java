package com.marcs.app.manager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marcs.app.manager.client.domain.Manager;
import com.marcs.app.manager.client.domain.ManagerDetail;
import com.marcs.app.manager.dao.ManagerDAO;
import com.marcs.app.vacation.client.VacationClient;
import com.marcs.app.vacation.client.domain.Vacation;

/**
 * Manager Service class that handles all service calls to the dao
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Component
public class ManagerService {

	@Autowired
	private VacationClient vacationClient;

	@Autowired
	private ManagerDAO managerDao;

	/**
	 * Service to get a list of managers for the logged in user
	 * 
	 * @return List of manager objects {@link Manager}
	 */
	public List<Manager> getManagers() {
		return managerDao.getManagers();
	}

	/**
	 * Service to get details of a manager given the manager id
	 * 
	 * @param id of the manager
	 * @return Manager Detail objects {@link ManagerDetail}
	 */
	public ManagerDetail getManagerDetail(int id) {
		ManagerDetail manager = managerDao.getManagerDetail(id);

		List<Vacation> vacations = vacationClient.getManagerVacations(id);
		manager.setVacations(vacations);

		return manager;
	}

	/**
	 * Service to get a list of store managers for the logged in user
	 * 
	 * @return List of manager objects {@link Manager}
	 */
	public List<Manager> getStoreManagers() {
		return managerDao.getStoreManagers();
	}
}
