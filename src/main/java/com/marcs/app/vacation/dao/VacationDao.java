package com.marcs.app.vacation.dao;

import static com.marcs.app.vacation.mapper.VacationMapper.VACATION_MAPPER;

import java.util.List;

import javax.sql.DataSource;

import com.marcs.app.vacation.client.domain.Vacation;
import com.marcs.app.vacation.client.domain.request.VacationRequest;
import com.marcs.common.abstracts.BaseDao;
import com.marcs.common.enums.VacationStatus;
import com.marcs.sql.SqlParamBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

/**
 * Class that handles all the dao calls to the database for vacations
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Component
public class VacationDao extends BaseDao {

	@Autowired
	public VacationDao(DataSource source) {
		super(source);
	}

	/**
	 * Get list of vacations for the current request.
	 * 
	 * @return {@link Vacation} object.
	 * @throws Exception
	 */
	public List<Vacation> getVacations(VacationRequest request) throws Exception {
		SqlParamBuilder builder = SqlParamBuilder.with(request).withParam("id", request.getId())
				.withParam("userId", request.getUserId())
				.withParam("regionalId", request.getRegionalId())
				.withParamTextEnumCollection("status", request.getStatus());
		MapSqlParameterSource params = builder.build();
		return getPage(getSql("getVacations", params), params, VACATION_MAPPER);
	}

	/**
	 * Get vacations for a report based on the current day.
	 * 
	 * @return {@link Lst<Vacation>} for the user.
	 * @throws Exception
	 */
	public List<Vacation> getVacationsForReport(VacationRequest request) throws Exception {
		SqlParamBuilder builder = SqlParamBuilder.with(request).withParam("id", request.getId())
				.withParam("userId", request.getUserId())
				.withParam("regionalId", request.getRegionalId())
				.withParamTextEnumCollection("status", request.getStatus())
				.withParam("reportFilter", true);
		MapSqlParameterSource params = builder.build();
		return getPage(getSql("getVacations", params), params, VACATION_MAPPER);
	}

	/**
	 * Create a vacation for the given user.
	 * 
	 * @param id  The user id to get vacations for.
	 * @param vac The vacation to be inserted.
	 * @return {@link Lst<Vacation>} for the user.
	 * @throws Exception
	 */
	public int createVacation(int id, Vacation vac) throws Exception {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		SqlParamBuilder builder = SqlParamBuilder.with().withParam("userId", id)
				.withParam("startDate", vac.getStartDate()).withParam("endDate", vac.getEndDate())
				.withParam("status", vac.getStatus() == null ? VacationStatus.APPROVED : vac.getStatus());
		MapSqlParameterSource params = builder.build();

		post(getSql("insertVacation"), params, keyHolder);
		return keyHolder.getKey().intValue();
	}

	/**
	 * This will update the vacations dates for the given vacation id.
	 * 
	 * @param id  The id of the vacation to be updates.
	 * @param vac What to update the vacation dates too.
	 * @return Vacation object of the updated information.
	 * @throws Exception
	 */
	public void updateVacationDatesById(int id, Vacation vac) throws Exception {
		SqlParamBuilder builder = SqlParamBuilder.with().withParam("startDate", vac.getStartDate()).withParam("id", id)
				.withParam("endDate", vac.getEndDate());
		MapSqlParameterSource params = builder.build();

		update(getSql("updateVacationDatesById"), params);
	}

	/**
	 * Update the values for a given vacation ID and object.
	 * 
	 * @param id  The id of the vacation inserted.
	 * @param vac What to update on for the vacation.
	 * @return {@link Vacation} object.
	 * @throws Exception
	 */
	public void updateVacationInfoById(int id, Vacation vac) throws Exception {
		SqlParamBuilder builder = SqlParamBuilder.with().withParam("status", vac.getStatus()).withParam("id", id)
				.withParam("notes", vac.getNotes());
		MapSqlParameterSource params = builder.build();
		update(getSql("updateVacationInfoById"), params);
	}

	/**
	 * Get a list of vacations for the given user id.
	 * 
	 * @param id The id for the vacation
	 * @return {@link Lst<Vacation>} for the user.
	 * @throws Exception
	 */
	public void deleteVacationById(int id) throws Exception {
		SqlParamBuilder builder = SqlParamBuilder.with().withParam("id", id);
		MapSqlParameterSource params = builder.build();
		delete(getSql("deleteVacations", params), params);
	}

	/**
	 * Deletes all user vacations for the given user id.
	 * 
	 * @param userId The user id to get vacations for.
	 * @return {@link Lst<Vacation>} for the user.
	 * @throws Exception
	 */
	public void deleteAllVacationsByUserId(int userId) throws Exception {
		SqlParamBuilder builder = SqlParamBuilder.with().withParam("userId", userId);
		MapSqlParameterSource params = builder.build();
		delete(getSql("deleteVacations", params), params);
	}
}
