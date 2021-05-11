package com.marcs.app.vacation.dao;

import static com.marcs.app.vacation.mapper.VacationMapper.VACATION_MAPPER;
import static com.marcs.sqlclient.SqlClient.getPage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.marcs.app.vacation.client.domain.Vacation;
import com.sql.service.parser.SqlBuilder;

/**
 * Class that handles all the dao calls to the database for vacations
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Component
public class VacationDAO {

	private SqlBuilder sqlBuilder = new SqlBuilder();

	/**
	 * This method returns a list of vacations based on the provided manager id
	 * 
	 * @param ids - array of user id's
	 * @return List of vacations
	 * 
	 * @since May 13, 2020
	 */
	public List<Vacation> getVacationsByManagerId(int id) {
		Map<String, String> params = new HashMap<>();
		params.put("id", String.valueOf(id));

		sqlBuilder.setQueryFile("vacationDAO");
		sqlBuilder.setParams(params);

		return getPage(sqlBuilder.getSql("getManagerVacations"), VACATION_MAPPER);
	}
}
