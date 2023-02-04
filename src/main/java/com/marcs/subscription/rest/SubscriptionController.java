/**
 * Copyright of Marcs App. All rights reserved.
 */
package com.marcs.subscription.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.marcs.annotations.interfaces.RestApiController;
import com.marcs.app.notifications.client.domain.Notification;
import com.marcs.subscription.client.domain.UserPrincipal;
import com.marcs.subscription.service.SubscriptionNotifierService;

@RequestMapping("/api/subscription-app")
@RestApiController
public class SubscriptionController {

    @Autowired
    private SubscriptionNotifierService service;

    /**
     * Will get the active users connected to the websocket session.
     * 
     * @return List of SimpUser connections.
     */
    @GetMapping(path = "/users")
    public List<UserPrincipal> getActiveSessionUsers() {
        return service.getActiveUserSessions();
    }

    /**
     * Push Notification to a specific user by id.
     * 
     * @param body The notification to push.
     */
    @PostMapping(path = "/user/notification")
    public void pushUserNotification(@RequestBody Notification body) {
        service.sendToUser(body, body.getReceiverId());
    }

    /**
     * Endpoint that will push the notitication to all users on the subscription of
     * the website.
     * 
     * @param body The body to send.
     */
    @PostMapping(path = "/all/notification")
    public void pushNotificationToAll(@RequestBody Notification body) {
        service.sendToAll(body);
    }
}
