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
     * Test endpoint for sending a notification body to the given user.
     */
    @PostMapping(path = "/user/notification")
    public void pushUserNotification(@RequestBody Notification body) {
        service.sendToUser(body, body.getReceiverId());
    }
}
