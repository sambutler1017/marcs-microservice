package com.marcs.app.store.service;

import java.util.List;

import com.marcs.app.store.client.domain.Store;
import com.marcs.app.store.client.domain.request.StoreGetRequest;
import com.marcs.app.store.dao.StoreDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Store Service class that handles all service calls to the dao
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Component
public class StoreService {

	@Autowired
	private StoreDao storeDao;

	/**
	 * Endpoint to get a list of stores based on the given request
	 * 
	 * @param request to filter stores on
	 * @return List of store objects {@link Store}
	 * @throws Exception
	 */
	public List<Store> getStores(StoreGetRequest request) throws Exception {
		return storeDao.getStores(request);
	}

	/**
	 * Gets the store information for the given storeId.
	 * 
	 * @param id The id of the store to get.
	 * @return {@link Store} object
	 * @throws Exception
	 */
	public Store getStoreById(String id) throws Exception {
		return storeDao.getStoreById(id);
	}
}
