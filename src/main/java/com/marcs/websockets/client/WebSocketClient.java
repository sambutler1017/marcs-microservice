package com.marcs.websockets.client;

import com.marcs.annotations.interfaces.Client;
import com.marcs.app.notifications.client.domain.Notification;
import com.marcs.websockets.service.WebSocketService;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Client for {@link WebSocketService} to expose the given endpoint's to other
 * services.
 * 
 * @author Sam Butler
 * @since Dec 14, 2020
 */
@Client
public class WebSocketClient {
    @Autowired
    private WebSocketService service;

    public void sendWebNotification(Notification message) throws Exception {
        service.sendWebNotification(message);
    }
}