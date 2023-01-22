package com.marcs.app.store.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.marcs.annotations.interfaces.Client;
import com.marcs.app.store.client.domain.Store;
import com.marcs.app.store.client.domain.request.StoreGetRequest;
import com.marcs.app.store.rest.StoreController;
import com.marcs.app.user.client.domain.User;

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
	 */
	public List<Store> getStores(StoreGetRequest request) {
		return controller.getStores(request);
	}

	/**
	 * Gets the store information for the given storeId.
	 * 
	 * @param id The id of the store to get.
	 * @return {@link Store} object
	 */
	public Store getStoreById(String id) {
		return controller.getStoreById(id);
	}

	/**
	 * Get the regional of the passed in store ID.
	 * 
	 * @return The regional of that store
	 */
	public User getRegionalOfStoreById(String storeId) {
		return controller.getRegionalOfStoreById(storeId);
	}

	/**
	 * Get the manager of the passed in store ID.
	 * 
	 * @return The manager of that store
	 */
	public User getManagerOfStoreById(String storeId) {
		return controller.getManagerOfStoreById(storeId);
	}

	/**
	 * This will update the manager of a store. It will take in a user id to update
	 * the manager too and a store Id to say what store the manager should be
	 * updated at.
	 * 
	 * @param userId  The user id of the manager.
	 * @param storeId The store Id to update the manager at.
	 * @return {@link Store} object with the updated manager.
	 */
	public Store updateStoreManagerOfStore(int userId, String storeId) {
		return controller.updateStoreManagerOfStore(userId, storeId);
	}

	/**
	 * This will update the regional of a store.
	 * 
	 * @param userId  The user id of the regional.
	 * @param storeId The store Id to update the regional at.
	 * @return {@link Store} object with the updated regional.
	 */
	public Store updateRegionalOfStore(int userId, String storeId) {
		return controller.updateRegionalOfStore(userId, storeId);
	}
}
