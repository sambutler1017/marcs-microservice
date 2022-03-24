package com.marcs.websockets;

import static com.marcs.websockets.client.domain.WebSocketGlobals.NOTIFICATIONS;

import com.marcs.annotations.interfaces.RestApiController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
    private SimpMessagingTemplate template;

    @PostMapping
    public void sendWebNotification(@RequestBody Object message) throws Exception {
        template.convertAndSend(NOTIFICATIONS, message);
    }
}
