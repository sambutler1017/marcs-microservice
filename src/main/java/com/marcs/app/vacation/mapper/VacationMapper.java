package com.marcs.app.vacation.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.marcs.app.vacation.client.domain.Vacation;
import com.marcs.common.abstracts.AbstractMapper;
import com.marcs.common.enums.VacationStatus;
import com.marcs.common.enums.WebRole;

/**
 * Mapper class to map a Vacation Object {@link Vacation}
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
public class VacationMapper extends AbstractMapper<Vacation> {
	public static VacationMapper VACATION_MAPPER = new VacationMapper();

	public Vacation mapRow(ResultSet rs, int rowNum) throws SQLException {
		Vacation vacation = new Vacation();
		vacation.setId(rs.getInt(ID));
		vacation.setUserId(rs.getInt(USER_ID));
		vacation.setStatus(VacationStatus.valueOf(rs.getString(STATUS)));
		vacation.setInsertDate(dateTimeFormat(rs.getString(INSERT_DATE)));
		vacation.setFullName(rs.getString(NAME).trim());
		vacation.setWebRole(WebRole.getRole(rs.getInt(WEB_ROLE_ID)));
		vacation.setStoreId(rs.getString(STORE_ID));
		vacation.setNotes(rs.getString(NOTES));
		try {
			vacation.setStartDate(dateTimeFormat(rs.getString(START_DATE)));
			vacation.setEndDate(dateTimeFormat(rs.getString(END_DATE)));
		}
		catch(Exception e) {
			vacation.setStartDate(null);
			vacation.setEndDate(null);
		}
		return vacation;
	}
}