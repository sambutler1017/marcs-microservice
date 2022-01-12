package com.marcs.app.featureAccess.rest;

import java.util.List;
import java.util.Map;

import com.marcs.annotations.interfaces.RestApiController;
import com.marcs.app.featureAccess.service.FeatureAccessService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
    private FeatureAccessService featureAccessService;

    /**
     * Gets the feature access in an application for user.
     * 
     * @return {@link Map} of the list of feature access.
     * @throws Exception
     */
    @GetMapping("/{webRoleId}")
    public Map<String, List<Map<String, String>>> getFeatureAccess(@PathVariable int webRoleId) throws Exception {
        return featureAccessService.getFeatureAccess(webRoleId);
    }
}
