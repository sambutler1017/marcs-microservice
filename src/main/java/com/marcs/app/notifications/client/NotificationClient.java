/**
 * Copyright of Marcs App. All rights reserved.
 */
package com.marcs.app.notifications.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Sets;
import com.marcs.annotations.interfaces.Client;
import com.marcs.app.email.client.EmailClient;
import com.marcs.app.notifications.client.domain.Notification;
import com.marcs.app.notifications.client.domain.request.NotificationGetRequest;
import com.marcs.app.notifications.service.ManageNotificationService;
import com.marcs.app.notifications.service.NotificationService;
import com.marcs.app.store.client.StoreClient;
import com.marcs.app.user.client.UserProfileClient;
import com.marcs.app.user.client.domain.User;
import com.marcs.app.vacation.client.domain.Vacation;
import com.marcs.common.enums.NotificationType;
import com.marcs.common.enums.WebRole;
import com.marcs.subscription.client.SubscriptionNotifierClient;

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
    private NotificationService notificationService;

    @Autowired
    private ManageNotificationService manageNotificationService;

    @Autowired
    private UserProfileClient userProfileClient;

    @Autowired
    private StoreClient storeClient;

    @Autowired
    private EmailClient emailClient;

    @Autowired
    private SubscriptionNotifierClient subscriptionNotifierClient;

    /**
     * This will get a list of notifications that the user has. The request will be
     * refined down to only notifications that are to them specifically, except for
     * ADMIN and SITE_ADMIN roles since they can approve any request (New User or
     * Vacations)
     * 
     * @param request The request with how to filter the request.
     * @return List of {@link Notification} objects.
     */
    public List<Notification> getNotifications(NotificationGetRequest request) {
        return notificationService.getNotifications(request).getList();
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
        return notificationService.getNotificationById(id);
    }

    /**
     * This will get the users current notifications. If none exist then it will
     * return an empty list.
     * 
     * @param read Boolean deciding if it should filter out read notifications
     * @return List of {@link Notification} objects.
     * @throws Exception If the notification can not be found
     */
    public List<Notification> getCurrentUserNotifications(NotificationGetRequest req) {
        return notificationService.getCurrentUserNotifications(req).getList();
    }

    /**
     * This will mark the passed in notification id as read. If the notification id
     * does not exist then it will throw an exception.
     * 
     * @param id The id to mark as read.
     * @return {@link Notification} object.
     */
    public Notification markNotificationRead(int id) throws Exception {
        return manageNotificationService.markNotificationRead(id);
    }

    /**
     * This will create a new notification specific to a {@link Vacation}. Once the
     * notification is created, it will send a web notification to the reciever of
     * the message.
     * 
     * @param vac The vacation to create a notification for.
     */
    public void sendNotification(Vacation vac) throws Exception {
        User userRequesting = userProfileClient.getUserById(vac.getUserId());
        Notification n = createNotification(userRequesting, vac.getId(), NotificationType.VACATION);
        sendWebNotification(n);
    }

    /**
     * This will create a new notification specific to a {@link User}. Once the
     * notification is created, it will send a web notification to the reciever of
     * the message.
     * 
     * @param user The user to create a notification for.
     */
    public void sendNotification(User user) throws Exception {
        Notification n = createNotification(user, user.getId(), NotificationType.USER);
        emailClient.sendNewUserEmail(user);
        sendWebNotification(n);
    }

    /**
     * Inserts the notification into the database to be tracked and stored.
     * 
     * @param u    The user who generated the notification
     * @param link The link to the data.
     * @param type The type of notification.
     * @return The created notification
     */
    public Notification createNotification(User u, int link, NotificationType type) throws Exception {
        Notification n = new Notification();

        n.setLinkId(link);
        n.setType(type);

        if(u.getWebRole().isManager()) {
            User regionalManager = storeClient.getRegionalManagerOfStoreById(u.getStoreId());
            n.setReceiverId(regionalManager != null ? regionalManager.getId() : 0);
        }
        else if(u.getWebRole().equals(WebRole.EMPLOYEE)) {
            User manager = storeClient.getManagerOfStoreById(u.getStoreId());
            n.setReceiverId(manager != null ? manager.getId() : 0);
        }
        else {
            n.setReceiverId(0); // Notification to only site admin and admins
        }
        return manageNotificationService.createNotification(n);
    }

    /**
     * Sends web notification to the desired users and web roles.
     * 
     * @param n The notification to be sent.
     */
    public void sendWebNotification(Notification n) {
        subscriptionNotifierClient.sendToUserWebRoles(n, Sets.newHashSet(WebRole.SITE_ADMIN, WebRole.ADMIN));
        subscriptionNotifierClient.sendToUser(n);
    }

    /**
     * This will delete the notification for the given id. If the id does not exist
     * to be deleted then it will return an exception.
     * 
     * @param id The id to be deleted
     */
    public void deleteNotification(int id) {
        manageNotificationService.deleteNotification(id);
    }
}
