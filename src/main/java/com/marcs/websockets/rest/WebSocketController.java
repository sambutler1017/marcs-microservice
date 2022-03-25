package com.marcs.websockets.rest;

import com.marcs.annotations.interfaces.RestApiController;
import com.marcs.app.notifications.client.domain.Notification;
import com.marcs.websockets.service.WebSocketService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * WebSocketController for notifications
 * 
 * @author Sam Butler
 * @since Dec 13, 2020
 */
@RestApiController
@RequestMapping("/api/web-notification-app/notifications")
public class WebSocketController {

    @Autowired
    private WebSocketService service;

    /**
     * Method that will send a web notification to the desired user. If the receiver
     * ID is null then it will not send it to the user and will only be sent to the
     * admins of the webnsite.
     * 
     * @param notification The notification to be sent.
     * @throws Exception
     */
    @PostMapping
    public void sendWebNotification(@RequestBody Notification notification) throws Exception {
        service.sendWebNotification(notification);
    }
}
