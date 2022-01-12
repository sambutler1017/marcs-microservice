package com.marcs.app.store.client;

import java.util.List;

import com.marcs.annotations.interfaces.Client;
import com.marcs.app.store.client.domain.Store;
import com.marcs.app.store.client.domain.request.StoreGetRequest;
import com.marcs.app.store.rest.StoreController;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * This class exposes the manager endpoint's to other app's to pull data across
 * the platform.
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Client
public class StoreClient {

	@Autowired
	private StoreController controller;

	/**
	 * Endpoint to get a list of stores based on the given request
	 * 
	 * @param request to filter stores on
	 * @return List of store objects {@link Store}
	 * @throws Exception
	 */
	public List<Store> getStores(StoreGetRequest request) throws Exception {
		return controller.getStores(request);
	}

	/**
	 * Gets the store information for the given storeId.
	 * 
	 * @param id The id of the store to get.
	 * @return {@link Store} object
	 * @throws Exception
	 */
	public Store getStoreById(String id) throws Exception {
		return controller.getStoreById(id);
	}
}
