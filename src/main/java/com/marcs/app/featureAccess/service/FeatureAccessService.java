package com.marcs.app.featureAccess.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marcs.app.featureAccess.dao.FeatureAccessDao;

/**
 * Feature Access Service
 *
 * @author Sam Butler
 * @since 8/2/2020
 */
@Service
public class FeatureAccessService {

    @Autowired
    private FeatureAccessDao dao;

    /**
     * Gets the feature access in an application for user.
     * 
     * @return {@link Map} of the list of feature access.
     */
    public Map<String, List<Map<String, String>>> getFeatureAccess(int roleId) {
        return dao.getFeatureAccess(roleId);
    }
}
