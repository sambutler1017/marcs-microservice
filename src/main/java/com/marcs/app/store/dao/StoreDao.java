package com.marcs.app.store.dao;

import static com.marcs.app.store.mapper.StoreMapper.STORE_MAPPER;
import static com.marcs.app.user.mapper.UserProfileMapper.USER_MAPPER;

import java.util.List;

import com.marcs.app.store.client.domain.Store;
import com.marcs.app.store.client.domain.request.StoreGetRequest;
import com.marcs.app.user.client.domain.User;
import com.marcs.common.abstracts.AbstractSqlDao;
import com.marcs.common.exceptions.StoreNotFoundException;

import org.springframework.stereotype.Repository;

/**
 * Class that handles all the dao calls to the database for stores
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Repository
public class StoreDao extends AbstractSqlDao {

	/**
	 * Endpoint to get a list of stores based on the given request
	 * 
	 * @param request to filter stores on
	 * @return List of store objects {@link Store}
	 * @throws Exception
	 * @since May 13, 2020
	 */
	public List<Store> getStores(StoreGetRequest request) throws Exception {
		return sqlClient.getPage(getSql("getStores"),
				params("id", request.getId()).addValue("regionalId", request.getRegionalId())
						.addValue("managerId", request.getManagerId()).addValue("name", request.getName()),
				STORE_MAPPER);
	}

	/**
	 * Gets the store information for the given storeId.
	 * 
	 * @param id The id of the store to get.
	 * @return {@link Store} object
	 * @throws Exception
	 */
	public Store getStoreById(String id) throws Exception {
		try {
			return sqlClient.getTemplate(getSql("getStoreById"), params("id", id), STORE_MAPPER);
		} catch (Exception e) {
			throw new StoreNotFoundException(String.format("Store could not be found for id: %s", id));
		}
	}

	/**
	 * Get the regional of the passed in store ID
	 * 
	 * @return The regional of that store
	 * @throws Exception
	 */
	public User getRegionalOfStoreById(String storeId) throws Exception {
		return sqlClient.getTemplate(getSql("getRegionalOfStore"), params("storeId", storeId), USER_MAPPER);
	}

	/**
	 * Get the manager of the passed in store ID.
	 * 
	 * @return The manager of that store
	 * @throws Exception
	 */
	public User getManagerOfStoreById(String storeId) throws Exception {
		return sqlClient.getTemplate(getSql("getManagerOfStoreById"), params("storeId", storeId), USER_MAPPER);
	}
}
