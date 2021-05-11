package com.marcs.app.manager.dao;

import static com.marcs.app.manager.mapper.ManagerMapper.MANAGER_MAPPER;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Sets;
import com.marcs.app.manager.client.domain.Manager;
import com.marcs.jwt.utility.JwtHolder;
import com.marcs.service.sql.SqlBuilder;
import com.marcs.service.sql.SqlClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Class that handles all the dao calls to the database for managers
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Component
public class ManagerDAO {

	@Autowired
	private JwtHolder jwtHolder;

	@Autowired
	private SqlClient sqlClient;
	
	@Autowired
	private SqlBuilder sqlBuilder;

	/**
	 * This method returns a list of managers based on the given array of user id's.
	 * It will return all columns from the database.
	 * 
	 * @return List of managers
	 * @since May 13, 2020
	 */
	public List<Manager> getManagers() {
		Map<String, Set<?>> params = new HashMap<>();
		params.put("id", Sets.newHashSet(jwtHolder.getRequiredUserId()));

		sqlBuilder.setQueryFile("managerDAO");
		sqlBuilder.setParams(params);

		return sqlClient.getPage(sqlBuilder.getSql("getManagers"), MANAGER_MAPPER);
	}
}
