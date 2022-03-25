package com.marcs.websockets.service;

import static com.marcs.websockets.client.domain.WebSocketGlobals.NOTIFICATIONS;

import com.marcs.app.notifications.client.domain.Notification;
import com.marcs.app.user.client.UserProfileClient;
import com.marcs.app.user.client.domain.User;
import com.marcs.app.user.client.domain.request.UserGetRequest;

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

    @Autowired
    private UserProfileClient userProfileClient;

    /**
     * Method that will send a web notification to the desired user. If the receiver
     * ID is null then it will not send it to the user and will only be sent to the
     * admins of the webnsite.
     * 
     * @param notification The notification to be sent.
     * @throws Exception
     */
    public void sendWebNotification(Notification notification) throws Exception {
        Integer receiver = notification.getReceiverId() == 0 ? null : notification.getReceiverId();

        if (receiver != null) {
            sendNotificationToUser(notification, receiver);
        }
        sendNotificationToRecevierRoles(notification);
    }

    /**
     * Method that will send the notification to all ADMINS and SITE_ADMINS on the
     * website.
     * 
     * @param notification The notification to be sent.
     * @throws Exception
     */
    private void sendNotificationToRecevierRoles(Notification notification) throws Exception {
        if (notification.getReceiverRoles() == null) {
            return;
        }

        UserGetRequest request = new UserGetRequest();
        request.setWebRole(notification.getReceiverRoles());

        for (User u : userProfileClient.getUsers(request)) {
            if (u.getId() != notification.getReceiverId()) {
                sendNotificationToUser(notification, u.getId());
            }
        }
    }

    /**
     * Helper method that will send a web notification to the passed in user id.
     * 
     * @param notification The notification to be sent.
     * @param userId       The user id of the user to send it too.
     */
    private void sendNotificationToUser(Notification notification, int userId) {
        template.convertAndSend(String.format("%s/%s", NOTIFICATIONS, userId), notification);
    }
}
