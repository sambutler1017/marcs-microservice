package com.marcs.app.store.service;

import java.util.List;

import com.google.common.collect.Sets;
import com.marcs.app.store.client.domain.Store;
import com.marcs.app.store.client.domain.request.StoreGetRequest;
import com.marcs.app.store.dao.StoreDao;
import com.marcs.app.user.client.UserProfileClient;
import com.marcs.app.user.client.domain.User;
import com.marcs.common.enums.WebRole;
import com.marcs.common.exceptions.BaseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

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

	@Autowired
	private UserProfileClient userProfileClient;

	/**
	 * Endpoint to get a list of stores based on the given request
	 * 
	 * @param request to filter stores on
	 * @return List of store objects {@link Store}
	 * @throws Exception
	 */
	public List<Store> getStores(StoreGetRequest request) throws Exception {
		return dao.getStores(request);
	}

	/**
	 * Gets the store information for the given storeId.
	 * 
	 * @param id The id of the store to get.
	 * @return {@link Store} object
	 * @throws Exception
	 */
	public Store getStoreById(String id) throws Exception {
		StoreGetRequest request = new StoreGetRequest();
		request.setId(Sets.newHashSet(id));
		return getStores(request).get(0);
	}

	/**
	 * Get the regional of the passed in store ID
	 * 
	 * @return The regional of that store
	 * @throws Exception
	 */
	public User getRegionalOfStoreById(String storeId) throws Exception {
		return dao.getRegionalOfStoreById(storeId);
	}

	/**
	 * Get the manager of the passed in store ID.
	 * 
	 * @return The manager of that store
	 * @throws Exception
	 */
	public User getManagerOfStoreById(String storeId) throws Exception {
		return dao.getManagerOfStoreById(storeId);
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
	public Store updateStore(String storeId, Store store) throws Exception {
		getStoreById(storeId);
		dao.updateStore(storeId, store);

		if (store.getRegionalId() > 0) {
			updateRegionalOfStore(store.getRegionalId(), store.getId());
		}
		return getStoreById(store.getId());
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
	public Store updateStoreManagerOfStore(int userId, String storeId) throws Exception {
		if (!userProfileClient.getUserById(userId).getWebRole().equals(WebRole.STORE_MANAGER)) {
			throw new BaseException(
					String.format(
							"User id '%d' is not a STORE_MANAGER web role. Can not update store manager of store!",
							userId));
		}

		int currentManagerId = getStoreById(storeId).getManagerId();
		if (currentManagerId != 0) {
			User updatedUserRole = new User();
			updatedUserRole.setWebRole(WebRole.ASSISTANT_MANAGER);
			userProfileClient.updateUserProfileById(currentManagerId, updatedUserRole);
		}

		dao.updateStoreManagerOfStore(userId, storeId);
		return getStoreById(storeId);
	}

	/**
	 * This will update the regional of a store.
	 * 
	 * @param userId  The user id of the regional.
	 * @param storeId The store Id to update the regional at.
	 * @return {@link Store} object with the updated regional.
	 * @throws Exception
	 */
	public Store updateRegionalOfStore(int userId, String storeId) throws Exception {
		if (userProfileClient.getUserById(userId).getWebRole().getRank() < WebRole.DISTRICT_MANAGER.getRank()) {
			throw new BaseException(
					String.format("User id '%d' is not a sufficient web role. Can not update overseer of store!",
							userId));
		}

		dao.updateRegionalOfStore(userId, storeId);
		return getStoreById(storeId);
	}

	/**
	 * This will create a new store for the given store id, store name, and regional
	 * on the store.
	 * 
	 * @param store The information to be created
	 * @return {@link Store} object with the created information.
	 * @throws Exception
	 */
	public Store createStore(Store store) throws Exception {
		Assert.notNull(store.getId(), "Store ID is a required field");
		Assert.notNull(store.getName(), "Store Name is a required field");
		Assert.isTrue(store.getRegionalId() > 0, "Regional ID must be greater than 0");

		return dao.createStore(store);
	}

	/**
	 * This will delete a store for the given store id.
	 * 
	 * @param storeId The store id of the store to be deleted.
	 * @throws Exception
	 */
	public void deleteStoreById(String storeId) throws Exception {
		getStoreById(storeId);
		dao.deleteStoreById(storeId);
	}
}
