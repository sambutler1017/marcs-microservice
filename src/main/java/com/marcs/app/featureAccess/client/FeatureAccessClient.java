/**
 * Copyright of Marcs App. All rights reserved.
 */
package com.marcs.app.featureaccess.client;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.marcs.annotations.interfaces.Client;
import com.marcs.app.featureaccess.rest.FeatureAccessController;

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
	 */
	public Map<String, List<Map<String, String>>> getFeatureAccess(int roleId) {
		return controller.getFeatureAccess(roleId);
	}
}
