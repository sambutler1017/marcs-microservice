package com.marcs.websockets.client;

import com.marcs.annotations.interfaces.Client;
import com.marcs.app.notifications.client.domain.Notification;
import com.marcs.websockets.rest.WebSocketController;
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
    private WebSocketController controller;

    /**
     * Method that will send a web notification to the desired user. If the receiver
     * ID is null then it will not send it to the user and will only be sent to the
     * admins of the webnsite.
     * 
     * @param notification The notification to be sent.
     * @throws Exception
     */
    public void sendWebNotification(Notification notification) throws Exception {
        controller.sendWebNotification(notification);
    }
}