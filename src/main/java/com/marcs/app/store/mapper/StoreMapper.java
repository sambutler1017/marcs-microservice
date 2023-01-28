/**
 * Copyright (c) 2023 Marcs App.
 * All rights reserved.
 */
package com.marcs.app.store.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.marcs.app.store.client.domain.Store;
import com.marcs.common.abstracts.AbstractMapper;

/**
 * Mapper class to map a Store Object {@link Store}
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
public class StoreMapper extends AbstractMapper<Store> {
	public static StoreMapper STORE_MAPPER = new StoreMapper();

	public Store mapRow(ResultSet rs, int rowNum) throws SQLException {
		Store store = new Store();
		store.setId(rs.getString(STORE_ID));
		store.setRegionalId(rs.getInt(REGIONAL_ID));
		store.setName(rs.getString(STORE_NAME));
		store.setManagerId(rs.getInt(MANAGER_ID));

		return store;
	}
}