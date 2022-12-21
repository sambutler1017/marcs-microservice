package com.marcs.app.vacation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marcs.app.notifications.client.NotificationClient;
import com.marcs.app.notifications.client.domain.Notification;
import com.marcs.app.user.client.UserProfileClient;
import com.marcs.app.vacation.client.domain.Vacation;
import com.marcs.app.vacation.dao.VacationDao;
import com.marcs.common.enums.NotificationType;
import com.marcs.common.enums.VacationStatus;
import com.marcs.jwt.utility.JwtHolder;
import com.marcs.subscription.client.SubscriptionNotifierClient;

/**
 * Vacation Service class that handles all service calls to the dao
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Component
public class ManageVacationService {

	@Autowired
	private VacationDao dao;

	@Autowired
	private JwtHolder jwtHolder;

	@Autowired
	private UserProfileClient userClient;

	@Autowired
	private NotificationClient notificationClient;

	@Autowired
	private SubscriptionNotifierClient subscriptionNotifierClient;

	@Autowired
	private VacationService vacationService;

	/**
	 * Create a vacation for the given user.
	 * 
	 * @param id  The user id to get vacations for.
	 * @param vac The vacation to be inserted.
	 * @return {@link List<Vacation>} for the user.
	 * @throws Exception
	 */
	public Vacation createVacation(int id, Vacation vac) throws Exception {
		userClient.getUserById(id);
		return vacationService.getVacationById(dao.createVacation(id, vac));
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
		Vacation returnVac = createVacation(jwtHolder.getUserId(), vac);
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
		for(Vacation vac : vacs) {
			createVacation(id, vac);
		}
		return vacationService.getVacationsByUserId(id);
	}

	/**
	 * Simple helper method to mark the vacations stored as expired if the date has
	 * already passed.
	 */
	public int markExpiredVacations() {
		return dao.markExpiredVacations();
	}

	/**
	 * Deletes all expired vacations in the given range. Range will be a value in
	 * months.
	 * 
	 * @param range The range to delete in months
	 * @return How many rows were deleted
	 */
	public int deleteAllExpiredVacations(int range) {
		return dao.deleteAllExpiredVacations(range);
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
		Vacation currentVac = vacationService.getVacationById(id);

		vac.setStartDate(vac.getStartDate() == null ? currentVac.getStartDate() : vac.getStartDate());
		vac.setEndDate(vac.getEndDate() == null ? currentVac.getEndDate() : vac.getEndDate());

		dao.updateVacationDatesById(id, vac);
		return vacationService.getVacationById(id);
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
		Vacation currentVac = vacationService.getVacationById(id);

		vac.setNotes(vac.getNotes() == null ? currentVac.getNotes() : vac.getNotes());
		vac.setStatus(vac.getStatus() == null ? currentVac.getStatus() : vac.getStatus());

		dao.updateVacationInfoById(id, vac);
		notifyUserBeingUpdated(currentVac.getUserId());
		return vacationService.getVacationById(id);
	}

	/**
	 * Deletes all vacations for the currently logged in user.
	 * 
	 * @param userId The user id to get vacations for.
	 * @return {@link Lst<Vacation>} for the user.
	 * @throws Exception
	 */
	public void deleteAllCurrentUserVacations() throws Exception {
		deleteAllVacationsByUserId(jwtHolder.getUserId());
	}

	/**
	 * Get a list of vacations for the given user id.
	 * 
	 * @param id The id for the vacation
	 * @return {@link List<Vacation>} for the user.
	 * @throws Exception
	 */
	public void deleteVacationById(int id) throws Exception {
		dao.deleteVacationById(id);
	}

	/**
	 * Deletes all user vacations for the given user id.
	 * 
	 * @param userId The user id to get vacations for.
	 * @return {@link Lst<Vacation>} for the user.
	 * @throws Exception
	 */
	public void deleteAllVacationsByUserId(int userId) throws Exception {
		dao.deleteAllVacationsByUserId(userId);
	}

	/**
	 * Method for sending web notification to the user that is being updated.
	 * 
	 * @param userId The userId to the notification too.
	 * @throws Exception
	 */
	private void notifyUserBeingUpdated(int userId) throws Exception {
		Notification n = new Notification();
		n.setType(NotificationType.REQUEST_TRACKER);
		subscriptionNotifierClient.sendToUser(n, userId);
	}
}
