package com.marcs.app.store.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Sets;
import com.marcs.app.store.client.domain.Store;
import com.marcs.app.store.client.domain.request.StoreGetRequest;
import com.marcs.app.store.dao.StoreDao;
import com.marcs.app.user.client.domain.User;

/**
 * Store Service class that handles all service calls to the dao
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Component
public class StoreService {

	@Autowired
	private StoreDao dao;

	/**
	 * Endpoint to get a list of stores based on the given request
	 * 
	 * @param request to filter stores on
	 * @return List of store objects {@link Store}
	 */
	public List<Store> getStores(StoreGetRequest request) {
		return dao.getStores(request);
	}

	/**
	 * Gets the store information for the given storeId.
	 * 
	 * @param id The id of the store to get.
	 * @return {@link Store} object
	 */
	public Store getStoreById(String id) {
		StoreGetRequest request = new StoreGetRequest();
		request.setId(Sets.newHashSet(id));
		return getStores(request).get(0);
	}

	/**
	 * Get the regional of the passed in store ID
	 * 
	 * @return The regional of that store
	 */
	public User getRegionalOfStoreById(String storeId) {
		return dao.getRegionalOfStoreById(storeId);
	}

	/**
	 * Get the manager of the passed in store ID.
	 * 
	 * @return The manager of that store
	 */
	public User getManagerOfStoreById(String storeId) {
		return dao.getManagerOfStoreById(storeId);
	}
}
