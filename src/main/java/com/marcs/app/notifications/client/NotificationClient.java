package com.marcs.app.notifications.client;

import java.util.List;

import com.marcs.annotations.interfaces.Client;
import com.marcs.app.email.client.EmailClient;
import com.marcs.app.notifications.client.domain.Notification;
import com.marcs.app.notifications.client.domain.request.NotificationGetRequest;
import com.marcs.app.notifications.rest.NotificationController;
import com.marcs.app.store.client.StoreClient;
import com.marcs.app.user.client.UserProfileClient;
import com.marcs.app.user.client.domain.User;
import com.marcs.app.vacation.client.domain.Vacation;
import com.marcs.common.enums.NotificationType;
import com.marcs.common.enums.WebRole;
import com.marcs.websockets.client.WebSocketClient;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * This class exposes the Request Manager endpoint's to other app's to pull data
 * across the platform.
 * 
 * @author Sam Butler
 * @since December 21, 2021
 */
@Client
public class NotificationClient {

    @Autowired
    private NotificationController controller;

    @Autowired
    private UserProfileClient userProfileClient;

    @Autowired
    private StoreClient storeClient;

    @Autowired
    private EmailClient emailClient;

    @Autowired
    private WebSocketClient websocketClient;

    /**
     * This will get a list of notifications that the user has. The request will be
     * refined down to only notifications that are to them specifically, except for
     * ADMIN and SITE_ADMIN roles since they can approve any request (New User or
     * Vacations)
     * 
     * @param request The request with how to filter the request.
     * @return List of {@link Notification} objects.
     * @throws Exception
     */
    public List<Notification> getNotifications(NotificationGetRequest request) throws Exception {
        return controller.getNotifications(request);
    }

    /**
     * This will get a specific notification by id. If the id does not exist or it
     * returns empty it will throw an exception, that the notification does not
     * exist.
     * 
     * @param id The id of the notification to get.
     * @return {@link Notification} objects.
     * @throws Exception If the notification can not be found
     */
    public Notification getNotificationById(int id) throws Exception {
        return controller.getNotificationById(id);
    }

    /**
     * This will get the users current notifications. If none exist then it will
     * return an empty list.
     * 
     * @param read Boolean deciding if it should filter out read notifications
     * @return List of {@link Notification} objects.
     * @throws Exception If the notification can not be found
     */
    public List<Notification> getCurrentUserNotifications(NotificationGetRequest req) throws Exception {
        return controller.getCurrentUserNotifications(req);
    }

    /**
     * This will mark the passed in notification id as read. If the notification id
     * does not exist then it will throw an exception.
     * 
     * @param id The id to mark as read.
     * @return {@link Notification} object.
     * @throws Exception
     */
    public Notification markNotificationRead(int id) throws Exception {
        return controller.markNotificationRead(id);
    }

    /**
     * This will create a new notification that needs inserted. It will contain the
     * type, link id, and who is receiving the notification.
     * 
     * @param n The notification that needs inserted.
     * @return {@link Notification} That is created.
     * @throws Exception
     */
    public Notification createNotification(Notification n) throws Exception {
        Notification generatedNotification = controller.createNotification(n);
        websocketClient.sendWebNotification(generatedNotification);
        return generatedNotification;
    }

    /**
     * This will create a new notification specific to a VACATION.
     * 
     * @param vac The vacation to create a notification for.
     * @throws Exception
     */
    public void createNotificationForVacation(Vacation vac) throws Exception {
        User userRequesting = userProfileClient.getUserById(vac.getUserId());
        Notification n = new Notification();

        n.setLinkId(vac.getId());
        n.setType(NotificationType.VACATION);

        if (userRequesting.getWebRole().isManager()) {
            n.setReceiverId(storeClient.getRegionalOfStoreById(userRequesting.getStoreId()).getId());
        } else if (userRequesting.getWebRole().equals(WebRole.EMPLOYEE)) {
            n.setReceiverId(storeClient.getManagerOfStoreById(userRequesting.getStoreId()).getId());
        } else {
            n.setReceiverId(0); // Notification to only site admin and admins
        }

        createNotification(n);
    }

    /**
     * This will create a new notification specific to a USER.
     * 
     * @param vac The vacation to create a notification for.
     * @throws Exception
     */
    public void createNotificationForUser(User user) throws Exception {
        Notification n = new Notification();
        n.setLinkId(user.getId());
        n.setType(NotificationType.USER);

        if (user.getWebRole().isManager()) {
            n.setReceiverId(storeClient.getRegionalOfStoreById(user.getStoreId()).getId());
        } else if (user.getWebRole().equals(WebRole.EMPLOYEE)) {
            n.setReceiverId(storeClient.getManagerOfStoreById(user.getStoreId()).getId());
        } else {
            n.setReceiverId(0); // Notification to only site admin and admins
        }

        emailClient.sendNewUserEmail(user);
        createNotification(n);
    }

    /**
     * This will delete the notification for the given id. If the id does not exist
     * to be deleted then it will return an exception.
     * 
     * @param id The id to be deleted
     * @throws Exception
     */
    public void deleteNotification(int id) throws Exception {
        controller.deleteNotification(id);
    }

}
