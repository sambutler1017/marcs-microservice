package com.marcs.app.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marcs.app.user.client.domain.UserAvailability;
import com.marcs.app.user.dao.UserAvailabilityDao;
import com.marcs.common.page.Page;

/**
 * User Availability Service class that handles all service calls to the dao
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Service
public class UserAvailabilityService {

    @Autowired
    private UserAvailabilityDao userAvailabilityDao;

    /**
     * Get list of user availability by user id
     * 
     * @param id The user id
     * @return Page of user availability
     */
    public Page<UserAvailability> getUserAvailabilityById(int id) {
        return userAvailabilityDao.getUserAvailabilityById(id);
    }
}
