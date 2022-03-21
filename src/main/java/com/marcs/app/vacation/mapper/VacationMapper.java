package com.marcs.app.vacation.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import com.marcs.app.vacation.client.domain.Vacation;
import com.marcs.common.enums.VacationStatus;
import com.marcs.common.enums.WebRole;

import org.springframework.jdbc.core.RowMapper;

/**
 * Mapper class to map a Vacation Object {@link Vacation}
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
public class VacationMapper implements RowMapper<Vacation> {
	public static VacationMapper VACATION_MAPPER = new VacationMapper();

	public Vacation mapRow(ResultSet rs, int rowNum) throws SQLException {
		Vacation vacation = new Vacation();
		vacation.setId(rs.getInt("id"));
		vacation.setUserId(rs.getInt("user_id"));
		vacation.setStatus(VacationStatus.valueOf(rs.getString("status")));
		vacation.setInsertDate(LocalDate.parse(rs.getDate("insert_date").toString()));
		vacation.setFullName(rs.getString("name").trim());
		vacation.setWebRole(WebRole.getRole(rs.getInt("web_role_id")));
		vacation.setStoreId(rs.getString("store_id"));
		vacation.setNotes(rs.getString("notes"));
		try {
			vacation.setStartDate(LocalDate.parse(rs.getDate("start_date").toString()));
			vacation.setEndDate(LocalDate.parse(rs.getDate("end_date").toString()));
		} catch (Exception e) {
			vacation.setStartDate(null);
			vacation.setEndDate(null);
		}
		return vacation;
	}
}