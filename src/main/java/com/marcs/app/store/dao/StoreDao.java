package com.marcs.app.store.dao;

import static com.marcs.app.store.mapper.StoreMapper.STORE_MAPPER;
import static com.marcs.app.user.mapper.UserProfileMapper.USER_MAPPER;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.marcs.app.store.client.domain.Store;
import com.marcs.app.store.client.domain.request.StoreGetRequest;
import com.marcs.app.user.client.domain.User;
import com.marcs.common.abstracts.BaseDao;
import com.marcs.sql.SqlParamBuilder;

/**
 * Class that handles all the dao calls to the database for stores
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Repository
public class StoreDao extends BaseDao {

	@Autowired
	public StoreDao(DataSource source) {
		super(source);
	}

	/**
	 * Endpoint to get a list of stores based on the given request
	 * 
	 * @param request to filter stores on
	 * @return List of store objects {@link Store}
	 * @throws Exception
	 * @since May 13, 2020
	 */
	public List<Store> getStores(StoreGetRequest request) throws Exception {
		MapSqlParameterSource params = SqlParamBuilder.with(request).useAllParams()
				.withParam(ID, request.getId())
				.withParam(REGIONAL_ID, request.getRegionalId())
				.withParam(MANAGER_ID, request.getManagerId())
				.withParam(NAME, request.getName()).build();
		return getList(getSql("getStores", params), params, STORE_MAPPER);
	}

	/**
	 * Get the regional of the passed in store ID
	 * 
	 * @return The regional of that store
	 * @throws Exception
	 */
	public User getRegionalOfStoreById(String storeId) throws Exception {
		try {
			return get(getSql("getRegionalOfStore"), parameterSource(ID, storeId), USER_MAPPER);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	/**
	 * Get the manager of the passed in store ID.
	 * 
	 * @return The manager of that store
	 * @throws Exception
	 */
	public User getManagerOfStoreById(String storeId) throws Exception {
		try {
			return get(getSql("getManagerOfStoreById"), parameterSource(ID, storeId), USER_MAPPER);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
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
	public void updateStore(String storeId, Store store) throws Exception {
		update(getSql("updateStore"), parameterSource("oldId", storeId).addValue("newId", store.getId())
				.addValue(NAME, store.getName()));
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
	public void updateStoreManagerOfStore(int userId, String storeId) throws Exception {
		update(getSql("updateStoreManagerByStoreId"),
				parameterSource(ID, storeId).addValue(MANAGER_ID, userId));
	}

	/**
	 * This will update the regional of a store.
	 * 
	 * @param userId  The user id of the regional.
	 * @param storeId The store Id to update the regional at.
	 * @return {@link Store} object with the updated regional.
	 * @throws Exception
	 */
	public void updateRegionalOfStore(int userId, String storeId) throws Exception {
		update(getSql("updateRegionalOfStore"),
				parameterSource(ID, storeId).addValue(REGIONAL_ID, userId));
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
		MapSqlParameterSource params = SqlParamBuilder.with().useAllParams()
				.withParam(ID, store.getId())
				.withParam(NAME, store.getName())
				.withParam(REGIONAL_ID, store.getRegionalId()).build();

		post(getSql("insertStore", params), params);
		return store;
	}

	/**
	 * This will delete a store for the given store id.
	 * 
	 * @param storeId The store id of the store to be deleted.
	 */
	public void deleteStoreById(String storeId) {
		delete(getSql("deleteStore"), parameterSource(ID, storeId));
	}
}
