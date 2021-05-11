package com.marcs.app.manager.client;

import java.util.List;

import com.marcs.app.manager.client.domain.Manager;
import com.marcs.app.manager.rest.ManagerController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
}
