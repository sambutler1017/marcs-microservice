package com.marcs.app.manager.mapper;

import static com.dataformatter.DateFormatter.formatDate;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.marcs.app.manager.client.domain.Manager;

/**
 * Mapper class to map a Manager Object {@link Manager}
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
public class ManagerMapper implements RowMapper<Manager> {
	public static ManagerMapper MANAGER_MAPPER = new ManagerMapper();

	public Manager mapRow(ResultSet rs, int rowNum) throws SQLException {
		Manager manager = new Manager();
		manager.setName(rs.getString("manager_name"));
		manager.setId(rs.getInt("manager_id"));
		manager.setRegionalId(rs.getInt("region_id"));
		manager.setStoreId(rs.getString("store_id"));
		manager.setStoreName(rs.getString("store_name"));
		try {
			manager.setStartDate(formatDate(rs.getDate("start_date")));
			manager.setEndDate(formatDate(rs.getDate("end_date")));
		} catch (Exception e) {
			manager.setStartDate("-");
			manager.setEndDate("-");
		}
		return manager;
	}
}