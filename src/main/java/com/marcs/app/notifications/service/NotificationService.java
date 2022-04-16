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
}
