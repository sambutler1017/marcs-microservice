package com.marcs.app.notifications.service;

import java.util.List;

import com.google.common.collect.Sets;
import com.marcs.app.notifications.client.domain.Notification;
import com.marcs.app.notifications.client.domain.request.NotificationGetRequest;
import com.marcs.app.notifications.dao.NotificationDao;
import com.marcs.jwt.utility.JwtHolder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Notification Service class that handles all service calls to the dao.
 * 
 * @author Sam Butler
 * @since December 25, 2021
 */
@Component
public class NotificationService {

    @Autowired
    private NotificationDao dao;

    @Autowired
    private JwtHolder jwtHolder;

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
        return dao.getNotifications(request);
    }

    /**
     * This will get a specific notification by id. If the id does not exist or it
     * returns empty it will
     * throw an exception, that the notification does not exist.
     * 
     * @param id The id of the notification to get.
     * @return {@link Notification} objects.
     * @throws Exception If the notification can not be found
     */
    public Notification getNotificationById(int id) throws Exception {
        NotificationGetRequest request = new NotificationGetRequest();
        request.setId(Sets.newHashSet(id));

        List<Notification> notifications = getNotifications(request);
        if (notifications.isEmpty()) {
            throw new Exception(String.format("Notification not found for id '%d'", id));
        }
        return notifications.get(0);
    }

    /**
     * This will get the users current notifications. If none exist then it will
     * return an empty list.
     * 
     * @return List of {@link Notification} objects.
     * @throws Exception If the notification can not be found
     */
    public List<Notification> getCurrentUserNotifications(NotificationGetRequest req) throws Exception {
        req.setReceiverId(Sets.newHashSet(jwtHolder.getRequiredUserId()));
        return getNotifications(req);
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
        Notification n = getNotificationById(id);
        dao.markNotificationRead(id);

        n.setRead(true);
        return n;
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
        if (n.getLinkId() == 0 || n.getReceiverId() == 0 || n.getType() == null) {
            throw new Exception("Link ID, Receiver ID, and Type are required fields");
        }
        return dao.createNotification(n);
    }

    /**
     * This will delete the notification for the given id. If the id does not exist
     * to be deleted then it will return an exception.
     * 
     * @param id The id to be deleted
     * @throws Exception
     */
    public void deleteNotification(int id) throws Exception {
        dao.deleteNotification(id);
    }
}
