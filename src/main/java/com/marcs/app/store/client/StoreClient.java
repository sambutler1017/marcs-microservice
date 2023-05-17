/**
 * Copyright of Marcs App. All rights reserved.
 */
package com.marcs.app.store.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.marcs.annotations.interfaces.Client;
import com.marcs.app.store.client.domain.Store;
import com.marcs.app.store.client.domain.request.StoreGetRequest;
import com.marcs.app.store.service.ManageStoreService;
import com.marcs.app.store.service.StoreService;
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
	private StoreService storeService;

	@Autowired
	private ManageStoreService manageStoreService;

	/**
	 * Endpoint to get a list of stores based on the given request
	 * 
	 * @param request to filter stores on
	 * @return List of store objects {@link Store}
	 */
	public List<Store> getStores(StoreGetRequest request) {
		return storeService.getStores(request).getList();
	}

	/**
	 * Gets the store information for the given storeId.
	 * 
	 * @param id The id of the store to get.
	 * @return {@link Store} object
	 */
	public Store getStoreById(String id) {
		return storeService.getStoreById(id);
	}

	/**
	 * Get the regional manager of the passed in store ID.
	 * 
	 * @return The regional manager of that store
	 */
	public User getRegionalManagerOfStoreById(String storeId) {
		return storeService.getRegionalManagerOfStoreById(storeId);
	}

	/**
	 * Get the manager of the passed in store ID.
	 * 
	 * @return The manager of that store
	 */
	public User getManagerOfStoreById(String storeId) {
		return storeService.getManagerOfStoreById(storeId);
	}

	/**
	 * This will update the information of a store. It will only be able to update
	 * the store id, store name, and regional manager of the store.
	 * 
	 * @param storeId The store Id to update the manager at.
	 * @param store   The information to be updated
	 * @return {@link Store} object with the updated information.
	 */
	public Store updateStore(String storeId, Store store) {
		return manageStoreService.updateStore(storeId, store);
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
		return manageStoreService.updateStoreManagerOfStore(userId, storeId);
	}

	/**
	 * This will update the regional manager of a store.
	 * 
	 * @param userId  The user id of the regional manager.
	 * @param storeId The store Id to update the regional manager at.
	 * @return {@link Store} object with the updated regional manager.
	 */
	public Store updateRegionalManagerOfStore(int userId, String storeId) {
		return manageStoreService.updateRegionalManagerOfStore(userId, storeId);
	}

	/**
	 * This will create a new store for the given store id, store name, and regional
	 * manager on the store.
	 * 
	 * @param store The information to be created
	 * @return {@link Store} object with the created information.
	 */
	public Store createStore(Store store) {
		return manageStoreService.createStore(store);
	}

	/**
	 * This will delete a store for the given store id.
	 * 
	 * @param storeId The store id of the store to be deleted.
	 */
	public void deleteStoreById(String storeId) {
		manageStoreService.deleteStoreById(storeId);
	}

	/**
	 * Will clear the manager from the store associated to that user.
	 * 
	 * @param userId The user id to clear
	 */
	public void clearStoreManager(int userId) {
		manageStoreService.clearStoreManager(userId);
	}

	/**
	 * Will clear the regional manager from all stores associated to that user.
	 * 
	 * @param userId The user id to clear
	 */
	public void clearRegionalManager(int userId) {
		manageStoreService.clearRegionalManager(userId);
	}
}
