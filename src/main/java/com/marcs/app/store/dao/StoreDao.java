/**
 * Copyright of Marcs App. All rights reserved.
 */
package com.marcs.app.store.dao;

import static com.marcs.app.store.mapper.StoreMapper.*;
import static com.marcs.app.user.mapper.UserProfileMapper.*;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.marcs.app.store.client.domain.Store;
import com.marcs.app.store.client.domain.request.StoreGetRequest;
import com.marcs.app.user.client.domain.User;
import com.marcs.common.abstracts.BaseDao;
import com.marcs.common.page.Page;
import com.marcs.sql.SqlParamBuilder;

/**
 * Class that handles all the dao calls to the database for stores
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Repository
public class StoreDao extends BaseDao {

	public StoreDao(DataSource source) {
		super(source);
	}

	/**
	 * Endpoint to get a list of stores based on the given request
	 * 
	 * @param request to filter stores on
	 * @return List of store objects {@link Store}
	 * @since May 13, 2020
	 */
	public Page<Store> getStores(StoreGetRequest request) {
		MapSqlParameterSource params = SqlParamBuilder.with(request).useAllParams().withParam(ID, request.getId())
				.withParam(REGIONAL_MANAGER_ID, request.getRegionalManagerId())
				.withParam(MANAGER_ID, request.getManagerId()).withParam(NAME, request.getName()).build();
		return getPage("getStoresPage", params, STORE_MAPPER);
	}

	/**
	 * Get the regional manager of the passed in store ID
	 * 
	 * @return The regional manager of that store
	 */
	public User getRegionalManagerOfStoreById(String storeId) {
		try {
			return get("getRegionalManagerOfStoreById", parameterSource(ID, storeId), USER_MAPPER);
		}
		catch(EmptyResultDataAccessException e) {
			return null;
		}
	}

	/**
	 * Get the manager of the passed in store ID.
	 * 
	 * @return The manager of that store
	 */
	public User getManagerOfStoreById(String storeId) {
		try {
			return get("getStoreManagerOfStoreById", parameterSource(ID, storeId), USER_MAPPER);
		}
		catch(EmptyResultDataAccessException e) {
			return null;
		}
	}

	/**
	 * This will update the information of a store. It will only be able to update
	 * the store id, store name, and regional manager of the store.
	 * 
	 * @param storeId The store Id to update the manager at.
	 * @param store   The information to be updated
	 * @return {@link Store} object with the updated information.
	 */
	public void updateStore(String storeId, Store store) {
		MapSqlParameterSource params = parameterSource("oldId", storeId).addValue("newId", store.getId())
				.addValue(NAME, store.getName());
		update("updateStore", params);
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
	public void updateStoreManagerOfStore(int userId, String storeId) {
		update("updateStoreManagerByStoreId", parameterSource(ID, storeId).addValue(MANAGER_ID, userId));
	}

	/**
	 * This will update the regional manager of a store.
	 * 
	 * @param userId  The user id of the regional manager.
	 * @param storeId The store Id to update the regional manager at.
	 * @return {@link Store} object with the updated regional manager.
	 */
	public void updateRegionalManagerOfStore(int userId, String storeId) {
		update("updateRegionalManagerOfStore", parameterSource(ID, storeId).addValue(REGIONAL_MANAGER_ID, userId));
	}

	/**
	 * This will create a new store for the given store id, store name, and regional
	 * manager on the store.
	 * 
	 * @param store The information to be created
	 * @return {@link Store} object with the created information.
	 */
	public Store createStore(Store store) {
		MapSqlParameterSource params = SqlParamBuilder.with().useAllParams().withParam(ID, store.getId())
				.withParam(NAME, store.getName()).withParam(REGIONAL_MANAGER_ID, store.getRegionalManagerId()).build();

		post("insertStore", params);
		return store;
	}

	/**
	 * This will delete a store for the given store id.
	 * 
	 * @param storeId The store id of the store to be deleted.
	 */
	public void deleteStoreById(String storeId) {
		delete("deleteStore", parameterSource(ID, storeId));
	}

	/**
	 * Will clear the manager from the store associated to that user.
	 * 
	 * @param userId The user id to clear
	 */
	public void clearStoreManager(int userId) {
		delete("clearStoreManager", parameterSource(USER_ID, userId));
	}

	/**
	 * Will clear the regional manager from all stores associated to that user.
	 * 
	 * @param userId The user id to clear
	 */
	public void clearRegionalManager(int userId) {
		delete("clearRegionalManager", parameterSource(USER_ID, userId));
	}
}
