package com.marcs.app.manager.rest;

import java.util.List;

import com.marcs.app.manager.client.domain.Manager;
import com.marcs.app.manager.service.ManagerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin()
@RestController
@RequestMapping("api/manager-app/managers")
public class ManagerController {

	@Autowired
	private ManagerService managerService;

	/**
	 * End point to a get a list of managers given a regional id. If the id given is
	 * not a regional that has managers or the regional has no managers it will
	 * return an empty list
	 * 
	 * @param id - regional id
	 * @return List of manager objects {@link Manager}
	 * @since May 13, 2020
	 */
	@GetMapping("/overview")
	public List<Manager> getMangers() {
		return managerService.getManagers();
	}
}
