package com.marcs.app.manager.service;

import java.util.List;

import com.marcs.app.manager.client.domain.Manager;
import com.marcs.app.manager.dao.ManagerDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Manager Service class that handles all service calls to the dao
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Component
public class ManagerService {

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
}
