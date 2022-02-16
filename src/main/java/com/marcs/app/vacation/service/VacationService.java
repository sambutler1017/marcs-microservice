package com.marcs.app.vacation.service;

import java.util.List;

import com.google.common.collect.Sets;
import com.marcs.app.notifications.client.NotificationClient;
import com.marcs.app.user.client.UserProfileClient;
import com.marcs.app.vacation.client.domain.Vacation;
import com.marcs.app.vacation.client.domain.request.VacationRequest;
import com.marcs.app.vacation.dao.VacationDao;
import com.marcs.common.enums.VacationStatus;
import com.marcs.jwt.utility.JwtHolder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Vacation Service class that handles all service calls to the dao
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Component
public class VacationService {

	@Autowired
	private VacationDao vacationDao;

	@Autowired
	private JwtHolder jwtHolder;

	@Autowired
	private UserProfileClient userClient;

	@Autowired
	private NotificationClient notificationClient;

	/**
	 * Get list of vacations for the current request.
	 * 
	 * @return {@link Vacation} object.
	 * @throws Exception
	 */
	public List<Vacation> getVacations(VacationRequest request) throws Exception {
		return vacationDao.getVacations(request);
	}

	/**
	 * Gets the current logged in users vacations.
	 * 
	 * @return {@link Vacation} object.
	 * @throws Exception
	 */
	public List<Vacation> getCurrentUserVacations() throws Exception {
		return getVacationsByUserId(jwtHolder.getRequiredUserId());
	}

	/**
	 * Get the vacation for the given id of the vacation.
	 * 
	 * @param id The id of the vacation inserted.
	 * @return {@link Vacation} object.
	 * @throws Exception
	 */
	public Vacation getVacationById(int id) throws Exception {
		VacationRequest request = new VacationRequest();
		request.setId(Sets.newHashSet(id));
		return getVacations(request).get(0);
	}

	/**
	 * Get a list of vacations for the given user id.
	 * 
	 * @param userId The user id to get vacations for.
	 * @return {@link Lst<Vacation>} for the user.
	 * @throws Exception
	 */
	public List<Vacation> getVacationsByUserId(int userId) throws Exception {
		VacationRequest request = new VacationRequest();
		request.setUserId(Sets.newHashSet(userId));
		return getVacations(request);
	}

	/**
	 * Get vacations for a report based on the current day.
	 * 
	 * @return {@link Lst<Vacation>} for the user.
	 * @throws Exception
	 */
	public List<Vacation> getVacationsForReport(VacationRequest request) throws Exception {
		return vacationDao.getVacationsForReport(request);
	}

	/**
	 * Create a vacation for the given user.
	 * 
	 * @param id  The user id to get vacations for.
	 * @param vac The vacation to be inserted.
	 * @return {@link Lst<Vacation>} for the user.
	 * @throws Exception
	 */
	public Vacation createVacation(int id, Vacation vac) throws Exception {
		userClient.getUserById(id);
		return getVacationById(vacationDao.createVacation(id, vac));
	}

	/**
	 * Request a vacation. This will be used by users that are asking to take a
	 * vacation.
	 * 
	 * @param vac The vacation to be inserted.
	 * @return {@link Vacation} for the user.
	 * @throws Exception
	 */
	public Vacation requestVacation(Vacation vac) throws Exception {
		vac.setStatus(VacationStatus.PENDING);
		Vacation returnVac = createVacation(jwtHolder.getRequiredUserId(), vac);
		notificationClient.createNotificationForVacation(returnVac);
		return returnVac;
	}

	/**
	 * Create multiple vacations and give them to the desired user file.
	 * 
	 * @param id   The user id to get vacations for.
	 * @param vacs The list of vacations to be inserted.
	 * @return {@link Lst<Vacation>} for the user.
	 * @throws Exception
	 */
	public List<Vacation> createBatchVacations(int id, List<Vacation> vacs) throws Exception {
		userClient.getUserById(id);
		for (Vacation vac : vacs) {
			createVacation(id, vac);
		}
		return getVacationsByUserId(id);
	}

	/**
	 * Simple helper method to mark the vacations stored as expired if the date has
	 * already passed.
	 */
	public void markExpiredVacations() {
		vacationDao.markExpiredVacations();
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
		Vacation currentVac = getVacationById(id);

		vac.setStartDate(vac.getStartDate() == null ? currentVac.getStartDate() : vac.getStartDate());
		vac.setEndDate(vac.getEndDate() == null ? currentVac.getEndDate() : vac.getEndDate());

		vacationDao.updateVacationDatesById(id, vac);
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
		Vacation currentVac = getVacationById(id);

		vac.setNotes(vac.getNotes() == null ? currentVac.getNotes() : vac.getNotes());
		vac.setStatus(vac.getStatus() == null ? currentVac.getStatus() : vac.getStatus());

		vacationDao.updateVacationInfoById(id, vac);
		return getVacationById(id);
	}

	/**
	 * Deletes all vacations for the currently logged in user.
	 * 
	 * @param userId The user id to get vacations for.
	 * @return {@link Lst<Vacation>} for the user.
	 * @throws Exception
	 */
	public void deleteAllCurrentUserVacations() throws Exception {
		deleteAllVacationsByUserId(jwtHolder.getRequiredUserId());
	}

	/**
	 * Get a list of vacations for the given user id.
	 * 
	 * @param id The id for the vacation
	 * @return {@link List<Vacation>} for the user.
	 * @throws Exception
	 */
	public void deleteVacationById(int id) throws Exception {
		vacationDao.deleteVacationById(id);
	}

	/**
	 * Deletes all user vacations for the given user id.
	 * 
	 * @param userId The user id to get vacations for.
	 * @return {@link Lst<Vacation>} for the user.
	 * @throws Exception
	 */
	public void deleteAllVacationsByUserId(int userId) throws Exception {
		vacationDao.deleteAllVacationsByUserId(userId);
	}
}
