package com.marcs.app.vacation.dao;

import static com.marcs.app.vacation.mapper.VacationMapper.VACATION_MAPPER;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.marcs.app.vacation.client.domain.Vacation;
import com.marcs.app.vacation.client.domain.request.VacationRequest;
import com.marcs.common.abstracts.AbstractSqlDao;
import com.marcs.common.exceptions.BaseException;
import com.marcs.common.exceptions.VacationNotFoundException;

import org.springframework.stereotype.Component;

/**
 * Class that handles all the dao calls to the database for vacations
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Component
public class VacationDao extends AbstractSqlDao {

	/**
	 * Get list of vacations for the current request.
	 * 
	 * @return {@link Vacation} object.
	 * @throws Exception
	 */
	public List<Vacation> getVacations(VacationRequest request) throws Exception {
		return sqlClient.getPage(
				getSql("getVacations"), params("userId", request.getUserId())
						.addValue("regionalId", request.getRegionalId()).addValue("status", request.getStatus()),
				VACATION_MAPPER);
	}

	/**
	 * Get the vacation for the given id of the vacation.
	 * 
	 * @param id The id of the vacation inserted.
	 * @return {@link Vacation} object.
	 * @throws Exception
	 */
	public Vacation getVacationById(int id) throws Exception {
		try {
			return sqlClient.getTemplate(getSql("getUserVacations"), params("id", id), VACATION_MAPPER);
		} catch (Exception e) {
			throw new VacationNotFoundException(String.format("Vacation Not found for id: %d", id));
		}
	}

	/**
	 * Get a list of vacations for the given user id.
	 * 
	 * @param userId The user id to get vacations for.
	 * @return {@link Lst<Vacation>} for the user.
	 * @throws Exception
	 */
	public List<Vacation> getVacationsByUserId(int userId) throws Exception {
		return sqlClient.getPage(getSql("getUserVacations"), params("userId", userId), VACATION_MAPPER);
	}

	/**
	 * Get vacations for a report based on the current day.
	 * 
	 * @return {@link Lst<Vacation>} for the user.
	 * @throws Exception
	 */
	public List<Vacation> getVacationsForReport(VacationRequest request) throws Exception {
		return sqlClient.getPage(getSql("getVacations"),
				params("userId", request.getUserId()).addValue("regionalId", request.getRegionalId())
						.addValue("status", request.getStatus()).addValue("startDate", LocalDate.now()),
				VACATION_MAPPER);
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
		Optional<Integer> autoId = sqlClient.post(getSql("insertVacation"),
				params("userId", id).addValue("startDate", vac.getStartDate()).addValue("endDate", vac.getEndDate())
						.addValue("status", vac.getStatus()));
		if (autoId.get() == -1) {
			throw new BaseException("Could not create Vacation");
		}
		return autoId.get();
	}

	/**
	 * This will update the vacations dates for the given vacation id.
	 * 
	 * @param id  The id of the vacation to be updates.
	 * @param vac What to update the vacation dates too.
	 * @return Vacation object of the updated information.
	 * @throws Exception
	 */
	public Vacation updateVacationDatesById(int id, Vacation vac) throws Exception {
		sqlClient.update(getSql("updateVacationDatesById"), params("startDate", vac.getStartDate()).addValue("id", id)
				.addValue("endDate", vac.getEndDate()));

		return getVacationById(id);
	}

	/**
	 * Update the values for a given vacation ID and object.
	 * 
	 * @param id  The id of the vacation inserted.
	 * @param vac What to update on for the vacation.
	 * @return {@link Vacation} object.
	 * @throws Exception
	 */
	public Vacation updateVacationInfoById(int id, Vacation vac) throws Exception {
		sqlClient.update(getSql("updateVacationInfoById"), params("status", vac.getStatus()).addValue("id", id)
				.addValue("notes", vac.getNotes() == null || vac.getNotes().trim().equals("") ? "-" : vac.getNotes()));

		return getVacationById(id);
	}

	/**
	 * Get a list of vacations for the given user id.
	 * 
	 * @param id The id for the vacation
	 * @return {@link Lst<Vacation>} for the user.
	 * @throws Exception
	 */
	public void deleteVacationById(int id) throws Exception {
		sqlClient.delete(getSql("deleteVacations"), params("id", id));
	}

	/**
	 * Deletes all user vacations for the given user id.
	 * 
	 * @param userId The user id to get vacations for.
	 * @return {@link Lst<Vacation>} for the user.
	 * @throws Exception
	 */
	public void deleteAllVacationsByUserId(int userId) throws Exception {
		sqlClient.delete(getSql("deleteVacations"), params("userId", userId));
	}
}
