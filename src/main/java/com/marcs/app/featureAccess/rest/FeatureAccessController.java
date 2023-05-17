/**
 * Copyright of Marcs App. All rights reserved.
 */
package com.marcs.app.featureaccess.rest;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.marcs.annotations.interfaces.RestApiController;
import com.marcs.app.featureaccess.service.FeatureAccessService;

/**
 * Gets feature access for a user
 *
 * @author Sam Butler
 * @since 8/3/2020
 */
@RequestMapping("api/feature-app/feature-access")
@RestApiController
public class FeatureAccessController {

    @Autowired
    private FeatureAccessService service;

    /**
     * Gets the feature access in an application for user.
     * 
     * @return {@link Map} of the list of feature access.
     */
    @GetMapping("/{webRoleId}")
    public Map<String, List<Map<String, String>>> getFeatureAccess(@PathVariable int webRoleId) {
        return service.getFeatureAccess(webRoleId);
    }
}
