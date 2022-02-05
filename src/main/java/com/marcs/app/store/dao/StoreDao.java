package com.marcs.app.store.dao;

import static com.marcs.app.store.mapper.StoreMapper.STORE_MAPPER;
import static com.marcs.app.user.mapper.UserProfileMapper.USER_MAPPER;

import java.util.List;

import javax.sql.DataSource;

import com.marcs.app.store.client.domain.Store;
import com.marcs.app.store.client.domain.request.StoreGetRequest;
import com.marcs.app.user.client.domain.User;
import com.marcs.common.abstracts.BaseDao;
import com.marcs.sql.SqlParamBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

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
		SqlParamBuilder builder = SqlParamBuilder.with(request).useAllParams().withParam("id", request.getId())
				.withParam("regionalId", request.getRegionalId())
				.withParam("managerId", request.getManagerId()).withParam("name", request.getName());
		MapSqlParameterSource params = builder.build();
		return getPage(getSql("getStores", params), params, STORE_MAPPER);
	}

	/**
	 * Get the regional of the passed in store ID
	 * 
	 * @return The regional of that store
	 * @throws Exception
	 */
	public User getRegionalOfStoreById(String storeId) throws Exception {
		return get(getSql("getRegionalOfStore"), parameterSource("storeId", storeId), USER_MAPPER);
	}

	/**
	 * Get the manager of the passed in store ID.
	 * 
	 * @return The manager of that store
	 * @throws Exception
	 */
	public User getManagerOfStoreById(String storeId) throws Exception {
		return get(getSql("getManagerOfStoreById"), parameterSource("storeId", storeId), USER_MAPPER);
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
				parameterSource("storeId", storeId).addValue("managerId", userId));
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
				parameterSource("storeId", storeId).addValue("regionalId", userId));
	}
}
