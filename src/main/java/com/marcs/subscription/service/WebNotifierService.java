/**
 * Copyright of Marcs App. All rights reserved.
 */
package com.marcs.subscription.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.marcs.app.notifications.client.domain.Notification;

/**
 * Web Notifier Service wraps the common elements of sending web notifications
 * into one call.
 * 
 * @author Sam Butler
 * @since March 24, 2022
 */
@Service
public class WebNotifierService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebNotifierService.class);

    @Autowired
    private SimpMessagingTemplate template;

    /**
     * Send a Web Notification for a given subscription match with the User
     * Notification set.
     * 
     * @param envelope {@link Notification} to be sent.
     */
    public void send(Notification body) {
        LOGGER.info("Sending Web Notification to '{}' with type '{}'", body.getDestination(), body.getType());
        sendNotification(body.getDestination(), body);
    }

    /**
     * Send a Web Notification for a given subscription match with the User
     * Notification set to a single user.
     * 
     * @param envelope    {@link Notification} to be sent.
     * @param sessionUUID The unique session id for the user.
     */
    public void send(Notification body, String sessionUUID) {
        String destination = String.format("%s-%s", body.getDestination(), sessionUUID);
        LOGGER.info("Sending Web Notification to '{}' with type '{}'", destination, body.getType());
        sendNotification(destination, body);
    }

    /**
     * Helper method for sending a web notification to the desired destination and
     * body.
     * 
     * @param <T>         The generic body type.
     * @param envelope    The body to be sent.
     * @param destination Where the notification should go.
     */
    private void sendNotification(String destination, Notification envelope) {
        template.convertAndSend(destination, envelope);
    }
}
