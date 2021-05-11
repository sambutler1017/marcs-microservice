package com.marcs.app.manager.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marcs.app.manager.client.domain.Manager;
import com.marcs.app.manager.client.domain.ManagerDetail;
import com.marcs.app.manager.service.ManagerService;

@CrossOrigin
@RestController
@RequestMapping("api/manager-app/managers")
@Controller
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

	/**
	 * End point to a get details of a manager given the id of the manager. If the
	 * id of the manager does not exist then this will return an empty object
	 * 
	 * @param id - manager id
	 * @return Manager Details object {@link ManagerDetail}
	 * @since May 13, 2020
	 */
	@GetMapping("/detail/{id}")
	public ManagerDetail getManagerDetail(@PathVariable int id) {
		return managerService.getManagerDetail(id);
	}

	/**
	 * End point to a get details of a manager given the id of the manager. If the
	 * id of the manager does not exist then this will return an empty object
	 * 
	 * @param id - manager id
	 * @return Manager Details object {@link ManagerDetail}
	 * @since May 13, 2020
	 */
	@GetMapping("/store-managers")
	public List<Manager> getStoreManagers() {
		return managerService.getStoreManagers();
	}
}
