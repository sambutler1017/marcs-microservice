package com.marcs.app.manager.mapper;

import static com.marcs.service.util.DateFormatter.formatDateText;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.marcs.app.manager.client.domain.ManagerDetail;

import org.springframework.jdbc.core.RowMapper;

/**
 * Mapper class to map a Manager Detail Object {@link MangerDetail}
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
public class ManagerDetailMapper implements RowMapper<ManagerDetail> {
	public static ManagerDetailMapper MANAGER_DETAIL_MAPPER = new ManagerDetailMapper();

	public ManagerDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
		ManagerDetail manager = new ManagerDetail();
		manager.setName(rs.getString("manager_name"));
		manager.setId(rs.getInt("manager_id"));
		manager.setRegionalId(rs.getInt("region_id"));
		manager.setStoreId(rs.getString("store_id"));
		manager.setStoreName(rs.getString("store_name"));
		manager.setStoreManager(rs.getBoolean("store_manager_flag"));
		manager.setHireDate(formatDateText(rs.getDate("hire_date")));
		return manager;
	}
}