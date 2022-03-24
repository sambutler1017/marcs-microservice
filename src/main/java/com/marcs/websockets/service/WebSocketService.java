package com.marcs.websockets.service;

import static com.marcs.websockets.client.domain.WebSocketGlobals.NOTIFICATIONS;

import com.marcs.app.notifications.client.domain.Notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * Service method for building a web notificaiton.
 * 
 * @author Sam Butler
 * @since March 23, 2022
 */
@Service
public class WebSocketService {
    @Autowired
    private SimpMessagingTemplate template;

    public void sendWebNotification(Notification message) throws Exception {
        template.convertAndSend(NOTIFICATIONS, message);
    }
}
