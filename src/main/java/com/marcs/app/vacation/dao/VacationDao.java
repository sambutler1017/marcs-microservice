/**
 * Copyright of Marcs App. All rights reserved.
 */
package com.marcs.app.vacation.dao;

import static com.marcs.app.vacation.mapper.VacationMapper.*;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.marcs.app.vacation.client.domain.Vacation;
import com.marcs.app.vacation.client.domain.request.VacationGetRequest;
import com.marcs.common.abstracts.BaseDao;
import com.marcs.common.enums.VacationStatus;
import com.marcs.common.page.Page;
import com.marcs.sql.SqlParamBuilder;

/**
 * Class that handles all the dao calls to the database for vacations
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Repository
public class VacationDao extends BaseDao {

	public VacationDao(DataSource source) {
		super(source);
	}

	/**
	 * Get list of vacations for the current request.
	 * 
	 * @return {@link Vacation} object.
	 */
	public Page<Vacation> getVacations(VacationGetRequest request) {
		MapSqlParameterSource params = SqlParamBuilder.with(request).usePagenation().withParam(ID, request.getId())
				.withParam(USER_ID, request.getUserId()).withParam(REGIONAL_ID, request.getRegionalId())
				.withParam(STORE_ID, request.getStoreId())
				.withParamTextEnumCollection(WEB_ROLE_TEXT_ID, request.getWebRole())
				.withParamTextEnumCollection(STATUS, request.getStatus()).build();

		return getPage("getVacationsPage", params, VACATION_MAPPER);
	}

	/**
	 * Get vacations for a report based on the current day.
	 * 
	 * @return {@link Lst<Vacation>} for the user.
	 */
	public List<Vacation> getVacationsForReport(VacationGetRequest request) {
		MapSqlParameterSource params = SqlParamBuilder.with(request).withParam(ID, request.getId())
				.withParam(USER_ID, request.getUserId()).withParam(REGIONAL_ID, request.getRegionalId())
				.withParam("reportFilter", true).withParamTextEnumCollection(STATUS, request.getStatus()).build();

		return getList("getVacations", params, VACATION_MAPPER);
	}

	/**
	 * Create a vacation for the given user.
	 * 
	 * @param id  The user id to get vacations for.
	 * @param vac The vacation to be inserted.
	 * @return {@link Lst<Vacation>} for the user.
	 */
	public int createVacation(int id, Vacation vac) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		MapSqlParameterSource params = SqlParamBuilder.with().withParam(USER_ID, id)
				.withParam(START_DATE, vac.getStartDate()).withParam(END_DATE, vac.getEndDate())
				.withParam(STATUS, vac.getStatus() == null ? VacationStatus.APPROVED : vac.getStatus())
				.withParam(NOTES, vac.getNotes()).build();

		post("insertVacation", params, keyHolder);
		return keyHolder.getKey().intValue();
	}

	/**
	 * Simple helper method to mark the vacations stored as expired if the date has
	 * already passed.
	 */
	public int markExpiredVacations() {
		MapSqlParameterSource params = SqlParamBuilder.with().build();
		return update("markExpiredVacations", params);
	}

	/**
	 * This will update the vacations dates for the given vacation id.
	 * 
	 * @param id  The id of the vacation to be updates.
	 * @param vac What to update the vacation dates too.
	 * @return Vacation object of the updated information.
	 */
	public void updateVacationDatesById(int id, Vacation vac) {
		MapSqlParameterSource params = SqlParamBuilder.with().withParam(ID, id)
				.withParam(START_DATE, vac.getStartDate()).withParam(END_DATE, vac.getEndDate()).build();

		update("updateVacationDatesById", params);
	}

	/**
	 * Update the values for a given vacation ID and object.
	 * 
	 * @param id  The id of the vacation inserted.
	 * @param vac What to update on for the vacation.
	 * @return {@link Vacation} object.
	 */
	public void updateVacationInfoById(int id, Vacation vac) {
		MapSqlParameterSource params = SqlParamBuilder.with().withParam(ID, id).withParam(STATUS, vac.getStatus())
				.withParam(NOTES, vac.getNotes()).build();

		update("updateVacationInfoById", params);
	}

	/**
	 * Get a list of vacations for the given user id.
	 * 
	 * @param id The id for the vacation
	 * @return {@link Lst<Vacation>} for the user.
	 */
	public void deleteVacationById(int id) {
		MapSqlParameterSource params = SqlParamBuilder.with().withParam(ID, id).build();

		delete("deleteVacations", params);
	}

	/**
	 * Deletes all user vacations for the given user id.
	 * 
	 * @param userId The user id to get vacations for.
	 * @return {@link Lst<Vacation>} for the user.
	 */
	public void deleteAllVacationsByUserId(int userId) {
		MapSqlParameterSource params = SqlParamBuilder.with().withParam(USER_ID, userId).build();
		delete("deleteVacations", params);
	}

	/**
	 * Deletes all expired vacations in the given range. Range will be a value in
	 * months.
	 * 
	 * @param range The range to delete in months
	 * @return How many rows were deleted
	 */
	public int deleteAllExpiredVacations(int range) {
		MapSqlParameterSource params = SqlParamBuilder.with().withParam(RANGE, range).build();
		return delete("deleteAllExpiredVacations", params);
	}
}
