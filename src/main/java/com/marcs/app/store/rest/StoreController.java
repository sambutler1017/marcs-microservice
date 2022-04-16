package com.marcs.app.store.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import com.marcs.annotations.interfaces.RestApiController;
import com.marcs.app.store.client.domain.Store;
import com.marcs.app.store.client.domain.request.StoreGetRequest;
import com.marcs.app.store.service.ManageStoreService;
import com.marcs.app.store.service.StoreService;
import com.marcs.app.user.client.domain.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestApiController
@RequestMapping("api/store-app/stores")
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
	 * @throws Exception
	 */
	@GetMapping(produces = APPLICATION_JSON_VALUE)
	public List<Store> getStores(StoreGetRequest request) throws Exception {
		return storeService.getStores(request);
	}

	/**
	 * Gets the store information for the given storeId.
	 * 
	 * @param id The id of the store to get.
	 * @return {@link Store} object
	 * @throws Exception
	 */
	@GetMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
	public Store getStoreById(@PathVariable String id) throws Exception {
		return storeService.getStoreById(id);
	}

	/**
	 * Get the regional of the passed in store ID.
	 * 
	 * @return The regional of that store
	 * @throws Exception
	 */
	@GetMapping(path = "/regional/{storeId}", produces = APPLICATION_JSON_VALUE)
	public User getRegionalOfStoreById(@PathVariable String storeId) throws Exception {
		return storeService.getRegionalOfStoreById(storeId);
	}

	/**
	 * Get the manager of the passed in store ID.
	 * 
	 * @return The manager of that store
	 * @throws Exception
	 */
	@GetMapping(path = "/manager/{storeId}", produces = APPLICATION_JSON_VALUE)
	public User getManagerOfStoreById(@PathVariable String storeId) throws Exception {
		return storeService.getManagerOfStoreById(storeId);
	}

	/**
	 * This will update the information of a store. It will only be able to update
	 * the store id, store name, and regional of the store.
	 * 
	 * @param storeId The store Id to update the manager at.
	 * @param store   The information to be updated
	 * @return {@link Store} object with the updated information.
	 * @throws Exception
	 */
	@PutMapping(path = "/{storeId}", produces = APPLICATION_JSON_VALUE)
	public Store updateStore(@PathVariable String storeId, @RequestBody Store store) throws Exception {
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
	 * @throws Exception
	 */
	@PutMapping(path = "{userId}/manager/{storeId}", produces = APPLICATION_JSON_VALUE)
	public Store updateStoreManagerOfStore(@PathVariable int userId, @PathVariable String storeId) throws Exception {
		return manageStoreService.updateStoreManagerOfStore(userId, storeId);
	}

	/**
	 * This will update the regional of a store.
	 * 
	 * @param userId  The user id of the regional.
	 * @param storeId The store Id to update the regional at.
	 * @return {@link Store} object with the updated regional.
	 * @throws Exception
	 */
	@PutMapping(path = "{userId}/regional/{storeId}", produces = APPLICATION_JSON_VALUE)
	public Store updateRegionalOfStore(@PathVariable int userId, @PathVariable String storeId) throws Exception {
		return manageStoreService.updateRegionalOfStore(userId, storeId);
	}

	/**
	 * This will create a new store for the given store id, store name, and regional
	 * on the store.
	 * 
	 * @param store The information to be created
	 * @return {@link Store} object with the created information.
	 * @throws Exception
	 */
	@PostMapping(produces = APPLICATION_JSON_VALUE)
	public Store createStore(@RequestBody Store store) throws Exception {
		return manageStoreService.createStore(store);
	}

	/**
	 * This will delete a store for the given store id.
	 * 
	 * @param storeId The store id of the store to be deleted.
	 * @throws Exception
	 */
	@DeleteMapping(path = "/{storeId}")
	public void deleteStoreById(@PathVariable String storeId) throws Exception {
		manageStoreService.deleteStoreById(storeId);
	}
}
