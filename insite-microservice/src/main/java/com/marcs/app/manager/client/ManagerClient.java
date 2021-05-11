package com.marcs.app.manager.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marcs.app.manager.client.domain.Manager;
import com.marcs.app.manager.client.domain.ManagerDetail;
import com.marcs.app.manager.rest.ManagerController;

/**
 * This class exposes the manager endpoint's to other app's to pull data across
 * the platform.
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Component
public class ManagerClient {

	@Autowired
	ManagerController controller;

	/**
	 * Client method to get managers for the logged in user
	 * 
	 * @param id of the regional to search under
	 * @return List of managers under that regional {@link Manager}
	 */
	public List<Manager> getMangers() {
		return controller.getMangers();
	}

	/**
	 * Client method to get details of a manager given the manager id
	 * 
	 * @param id of the manager
	 * @return Manager detail object {@link ManagerDetail}
	 */
	public ManagerDetail getManagerDetail(int id) {
		return controller.getManagerDetail(id);
	}

	/**
	 * Client method to get list of store managers from the logged in user
	 * 
	 * @return List of Manager objects {@link Manager}
	 */
	public List<Manager> getStoreManagers() {
		return controller.getStoreManagers();
	}
}
