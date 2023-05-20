package com.marcs.app.user.rest;

import static org.springframework.http.MediaType.*;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.marcs.app.user.client.domain.UserAvailability;

@RequestMapping("/api/users/availability")
@RestController
public class UserAvailabilityController {

    /**
     * Insert a user availability for the given user id.
     * 
     * @param userAvailability The avaiablity object for a user.
     * @return The created availability object with the attached creation id.
     */
    @PostMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public UserAvailability createUserAvailability(@RequestBody @Valid UserAvailability userAvailability) {
        return null;
    }
}
