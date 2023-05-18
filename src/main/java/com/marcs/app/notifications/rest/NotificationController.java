/**
 * Copyright of Marcs App. All rights reserved.
 */
package com.marcs.app.notifications.rest;

import static org.springframework.http.MediaType.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.marcs.annotations.interfaces.RestApiController;
import com.marcs.app.notifications.client.domain.Notification;
import com.marcs.app.notifications.client.domain.request.NotificationGetRequest;
import com.marcs.app.notifications.service.ManageNotificationService;
import com.marcs.app.notifications.service.NotificationService;
import com.marcs.common.page.Page;

@RestApiController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private ManageNotificationService manageNotificationService;

    /**
     * This will get a list of notifications that the user has. The request will be
     * refined down to only notifications that are to them specifically, except for
     * ADMIN and SITE_ADMIN roles since they can approve any request (New User or
     * Vacations)
     * 
     * @param request The request with how to filter the request.
     * @return Page of {@link Notification} objects
     */
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public Page<Notification> getNotifications(NotificationGetRequest request) {
        return notificationService.getNotifications(request);
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
    @GetMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
    public Notification getNotificationById(@PathVariable int id) throws Exception {
        return notificationService.getNotificationById(id);
    }

    /**
     * This will get the users current notifications. If none exist then it will
     * return an empty list.
     * 
     * @param read Boolean deciding if it should filter out read notifications
     * @return Page of {@link Notification} objects.
     * @throws Exception If the notification can not be found
     */
    @GetMapping(path = "/current-user", produces = APPLICATION_JSON_VALUE)
    public Page<Notification> getCurrentUserNotifications(NotificationGetRequest req) {
        return notificationService.getCurrentUserNotifications(req);
    }

    /**
     * This will mark the passed in notification id as read. If the notification id
     * does not exist then it will throw an exception.
     * 
     * @param id The id to mark as read.
     * @return {@link Notification} object.
     */
    @PutMapping(path = "/{id}/read", produces = APPLICATION_JSON_VALUE)
    public Notification markNotificationRead(@PathVariable int id) throws Exception {
        return manageNotificationService.markNotificationRead(id);
    }

    /**
     * This will create a new notification that needs inserted. It will contain the
     * type, link id, and who is receiving the notification.
     * 
     * @param n The notification that needs inserted.
     * @return {@link Notification} That is created.
     */
    @PostMapping(produces = APPLICATION_JSON_VALUE)
    public Notification createNotification(@RequestBody Notification n) throws Exception {
        return manageNotificationService.createNotification(n);
    }

    /**
     * This will delete the notification for the given id. If the id does not exist
     * to be deleted then it will return an exception.
     * 
     * @param id The id to be deleted
     */
    @DeleteMapping(path = "/{id}")
    public void deleteNotification(@PathVariable int id) {
        manageNotificationService.deleteNotification(id);
    }
}
