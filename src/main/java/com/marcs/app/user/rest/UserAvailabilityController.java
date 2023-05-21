package com.marcs.app.user.rest;

import static org.springframework.http.MediaType.*;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.marcs.app.user.client.domain.UserAvailability;
import com.marcs.app.user.service.ManageUserAvailabilityService;
import com.marcs.app.user.service.UserAvailabilityService;
import com.marcs.common.page.Page;

@RequestMapping("/api/users/availability")
@RestController
public class UserAvailabilityController {

    @Autowired
    private UserAvailabilityService userAvailabilityService;

    @Autowired
    private ManageUserAvailabilityService manageUserAvailabilityService;

    /**
     * Get list of user availability by user id
     * 
     * @param id The user id
     * @return Page of user availability
     */
    @GetMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
    public Page<UserAvailability> getUserAvailabilityById(@PathVariable int id) {
        return userAvailabilityService.getUserAvailabilityById(id);
    }

    /**
     * Insert a user availability for the given user id.
     * 
     * @param userAvailability The avaiablity object for a user.
     * @return The created availability object with the attached creation id.
     */
    @PostMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public UserAvailability createUserAvailability(@RequestBody @Valid UserAvailability userAvailability) {
        return manageUserAvailabilityService.createUserAvailability(userAvailability);
    }
}
