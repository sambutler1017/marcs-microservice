package com.marcs.app.vacation.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marcs.app.vacation.client.domain.Vacation;
import com.marcs.app.vacation.service.VacationService;

@CrossOrigin
@RestController
@RequestMapping("api/vacation-app/vacations")
@Controller
public class VacationController {

	@Autowired
	private VacationService vacationSerivce;

	@GetMapping("/{id}")
	public List<Vacation> getManagerVacations(@PathVariable int id) {
		return vacationSerivce.getVacationsByManagerId(id);
	}
}