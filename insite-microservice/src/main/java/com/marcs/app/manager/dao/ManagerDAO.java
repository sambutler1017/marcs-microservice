package com.marcs.app.manager.dao;

import static com.marcs.app.manager.mapper.ManagerDetailMapper.MANAGER_DETAIL_MAPPER;
import static com.marcs.app.manager.mapper.ManagerMapper.MANAGER_MAPPER;
import static com.marcs.sqlclient.SqlClient.getPage;
import static com.marcs.sqlclient.SqlClient.getTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marcs.app.manager.client.domain.Manager;
import com.marcs.app.manager.client.domain.ManagerDetail;
import com.marcs.jwt.service.JwtHolder;
import com.sql.service.parser.SqlBuilder;

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

	private SqlBuilder sqlBuilder = new SqlBuilder();

	/**
	 * This method returns a list of managers based on the given array of user id's.
	 * It will return all columns from the database.
	 * 
	 * @return List of managers
	 * @since May 13, 2020
	 */
	public List<Manager> getManagers() {
		Map<String, String> params = new HashMap<>();
		params.put("id", String.valueOf(jwtHolder.getRequiredUserId()));

		sqlBuilder.setQueryFile("managerDAO");
		sqlBuilder.setParams(params);

		return getPage(sqlBuilder.getSql("getManagers"), MANAGER_MAPPER);
	}

	/**
	 * This method returns a manager detail object passed in a manager id to look
	 * for in the database.
	 * 
	 * @param ids - manager id
	 * @return A ManagerDetail Object
	 * @since June 17, 2020
	 */
	public ManagerDetail getManagerDetail(int id) {
		Map<String, String> params = new HashMap<>();
		params.put("id", String.valueOf(id));

		sqlBuilder.setQueryFile("managerDAO");
		sqlBuilder.setParams(params);

		return getTemplate(sqlBuilder.getSql("getManagerById"), MANAGER_DETAIL_MAPPER);
	}

	/**
	 * This method returns a list of store managers that belong to a regional
	 * 
	 * @return List of managers
	 * @since May 13, 2020
	 */
	public List<Manager> getStoreManagers() {
		Map<String, String> params = new HashMap<>();
		params.put("id", String.valueOf(jwtHolder.getRequiredUserId()));
		params.put("flag", "1");

		sqlBuilder.setQueryFile("managerDAO");
		sqlBuilder.setParams(params);

		return getPage(sqlBuilder.getSql("getManagers"), MANAGER_MAPPER);
	}
}
