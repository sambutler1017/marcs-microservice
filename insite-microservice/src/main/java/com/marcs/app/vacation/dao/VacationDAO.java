package com.marcs.app.vacation.dao;

import static com.marcs.app.vacation.mapper.VacationMapper.VACATION_MAPPER;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Sets;
import com.marcs.app.vacation.client.domain.Vacation;
import com.marcs.service.sql.SqlBuilder;
import com.marcs.service.sql.SqlClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Class that handles all the dao calls to the database for vacations
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Component
public class VacationDAO {

	@Autowired
	private SqlClient sqlClient;
	
	@Autowired
	private SqlBuilder sqlBuilder;

	/**
	 * This method returns a list of vacations based on the provided manager id
	 * 
	 * @param ids - array of user id's
	 * @return List of vacations
	 * 
	 * @since May 13, 2020
	 */
	public List<Vacation> getVacationsByManagerId(int id) {
		Map<String, Set<?>> params = new HashMap<>();
		params.put("id", Sets.newHashSet(id));

		sqlBuilder.setQueryFile("vacationDAO");
		sqlBuilder.setParams(params);

		return sqlClient.getPage(sqlBuilder.getSql("getManagerVacations"), VACATION_MAPPER);
	}
}
