package com.marcs.app.featureAccess.client;

import java.util.List;
import java.util.Map;

import com.marcs.annotations.interfaces.Client;
import com.marcs.app.featureAccess.rest.FeatureAccessController;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * This class exposes the feature access endpoint's to other app's to pull data
 * across the platform.
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Client
public class FeatureAccessClient {

	@Autowired
	FeatureAccessController controller;

	/**
	 * Client method to get list of user feature access
	 * 
	 * @param request to filter stores on
	 * @return List of stores {@link Map<String,String>}
	 * @throws Exception
	 */
	public Map<String, List<Map<String, String>>> getFeatureAccess(int roleId) throws Exception {
		return controller.getFeatureAccess(roleId);
	}
}
