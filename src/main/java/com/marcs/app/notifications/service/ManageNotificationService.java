package com.marcs.app.notifications.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marcs.app.notifications.client.domain.Notification;
import com.marcs.app.notifications.dao.NotificationDao;

/**
 * Notification Service class that handles all service calls to the dao.
 * 
 * @author Sam Butler
 * @since December 25, 2021
 */
@Component
public class ManageNotificationService {

    @Autowired
    private NotificationDao dao;

    @Autowired
    private NotificationService notificationService;

    /**
     * This will mark the passed in notification id as read. If the notification id
     * does not exist then it will throw an exception.
     * 
     * @param id The id to mark as read.
     * @return {@link Notification} object.
     * @throws Exception
     */
    public Notification markNotificationRead(int id) throws Exception {
        Notification n = notificationService.getNotificationById(id);
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
        if(n.getLinkId() == 0 || n.getType() == null) {
            throw new Exception("Link ID, and Type are required fields");
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
    public void deleteNotification(int id) {
        dao.deleteNotification(id);
    }
}
