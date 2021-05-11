package com.marcs.app.vacation.mapper;

import static com.dataformatter.DateFormatter.formatDateTextShort;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.marcs.app.vacation.client.domain.Vacation;

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
		vacation.setId(rs.getInt("vacation_id"));
		try {
			vacation.setStartDate(formatDateTextShort(rs.getDate("start_date")));
			vacation.setEndDate(formatDateTextShort(rs.getDate("end_date")));
		} catch (Exception e) {
			vacation.setStartDate("-");
			vacation.setEndDate("-");
		}
		return vacation;
	}
}