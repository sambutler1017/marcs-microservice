package com.marcs.app.user.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marcs.app.user.client.domain.UserCredentials;
import com.marcs.app.user.client.domain.UserProfile;
import com.marcs.app.user.client.domain.UserSecurity;
import com.marcs.app.user.client.domain.request.UserCredentialsGetRequest;
import com.marcs.app.user.service.UserService;

@CrossOrigin
@RestController
@RequestMapping("api/user-app/users")
@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/profile/{id}")
	public UserProfile getUserById(@PathVariable int id) {
		return userService.getUserById(id);
	}

	@GetMapping("/credentials")
	public UserCredentials getUserCredentials(@RequestBody UserCredentialsGetRequest request) {
		return userService.getUserCredentials(request);
	}

	@GetMapping("/security/{id}")
	public UserSecurity getUserSecurity(@PathVariable int id) {
		return userService.getUserSecurity(id);
	}
}