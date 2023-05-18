/**
 * Copyright of Marcs App. All rights reserved.
 */
package com.marcs.app.store.rest;

import static org.springframework.http.MediaType.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.marcs.annotations.interfaces.RestApiController;
import com.marcs.app.store.client.domain.Store;
import com.marcs.app.store.client.domain.request.StoreGetRequest;
import com.marcs.app.store.service.ManageStoreService;
import com.marcs.app.store.service.StoreService;
import com.marcs.app.user.client.domain.User;
import com.marcs.common.page.Page;

@RestApiController
@RequestMapping("/api/stores")
public class StoreController {

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
	@GetMapping(produces = APPLICATION_JSON_VALUE)
	public Page<Store> getStores(StoreGetRequest request) {
		return storeService.getStores(request);
	}

	/**
	 * Gets the store information for the given storeId.
	 * 
	 * @param id The id of the store to get.
	 * @return {@link Store} object
	 */
	@GetMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
	public Store getStoreById(@PathVariable String id) {
		return storeService.getStoreById(id);
	}

	/**
	 * Get the regional manager of the passed in store ID.
	 * 
	 * @return The regional manager of that store
	 */
	@GetMapping(path = "/regional-manager/{storeId}", produces = APPLICATION_JSON_VALUE)
	public User getRegionalManagerOfStoreById(@PathVariable String storeId) {
		return storeService.getRegionalManagerOfStoreById(storeId);
	}

	/**
	 * Get the manager of the passed in store ID.
	 * 
	 * @return The manager of that store
	 */
	@GetMapping(path = "/manager/{storeId}", produces = APPLICATION_JSON_VALUE)
	public User getManagerOfStoreById(@PathVariable String storeId) {
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
	@PutMapping(path = "/{storeId}", produces = APPLICATION_JSON_VALUE)
	public Store updateStore(@PathVariable String storeId, @RequestBody Store store) {
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
	@PutMapping(path = "{userId}/manager/{storeId}", produces = APPLICATION_JSON_VALUE)
	public Store updateStoreManagerOfStore(@PathVariable int userId, @PathVariable String storeId) {
		return manageStoreService.updateStoreManagerOfStore(userId, storeId);
	}

	/**
	 * This will update the regional manager of a store.
	 * 
	 * @param userId  The user id of the regional manager.
	 * @param storeId The store Id to update the regional manager at.
	 * @return {@link Store} object with the updated regional manager.
	 */
	@PutMapping(path = "{userId}/regional-manager/{storeId}", produces = APPLICATION_JSON_VALUE)
	public Store updateRegionalManagerOfStore(@PathVariable int userId, @PathVariable String storeId) {
		return manageStoreService.updateRegionalManagerOfStore(userId, storeId);
	}

	/**
	 * This will create a new store for the given store id, store name, and regional
	 * manager on the store.
	 * 
	 * @param store The information to be created
	 * @return {@link Store} object with the created information.
	 */
	@PostMapping(produces = APPLICATION_JSON_VALUE)
	public Store createStore(@RequestBody Store store) {
		return manageStoreService.createStore(store);
	}

	/**
	 * This will delete a store for the given store id.
	 * 
	 * @param storeId The store id of the store to be deleted.
	 */
	@DeleteMapping(path = "/{storeId}")
	public void deleteStoreById(@PathVariable String storeId) {
		manageStoreService.deleteStoreById(storeId);
	}

	/**
	 * Will clear the manager from the store associated to that user.
	 * 
	 * @param userId The user id to clear
	 */
	@DeleteMapping(path = "/manager/{userId}")
	public void clearStoreManager(@PathVariable int userId) {
		manageStoreService.clearStoreManager(userId);
	}

	/**
	 * Will clear the regional manager from all stores associated to that user.
	 * 
	 * @param userId The user id to clear
	 */
	@DeleteMapping(path = "/regional-manager/{userId}")
	public void clearRegionalManager(@PathVariable int userId) {
		manageStoreService.clearRegionalManager(userId);
	}
}
