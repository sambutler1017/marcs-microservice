package com.marcs.app.store.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.marcs.app.store.client.domain.Store;

import org.springframework.jdbc.core.RowMapper;

/**
 * Mapper class to map a Store Object {@link Store}
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
public class StoreMapper implements RowMapper<Store> {
	public static StoreMapper STORE_MAPPER = new StoreMapper();

	public Store mapRow(ResultSet rs, int rowNum) throws SQLException {
		Store store = new Store();
		store.setId(rs.getString("id"));
		store.setRegionalId(rs.getInt("regional_id"));
		store.setName(rs.getString("name"));
		store.setManagerId(rs.getInt("manager_id"));

		return store;
	}
}