package com.marcs.app.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marcs.app.user.client.domain.UserAvailability;
import com.marcs.app.user.dao.UserAvailabilityDao;

/**
 * User Service class that handles all service calls to the dao
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Service
public class ManageUserAvailabilityService {

    @Autowired
    private UserAvailabilityDao userAvailabilityDao;

    /**
     * Insert a user availability for the given user id.
     * 
     * @param userAvailability The avaiablity object for a user.
     * @return The created availability object with the attached creation id.
     */
    public UserAvailability createUserAvailability(UserAvailability userAvailability) {
        return userAvailabilityDao.createUserAvailability(userAvailability);
    }
}
