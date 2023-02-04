/**
 * Copyright of Marcs App. All rights reserved.
 */
package com.marcs.subscription.client;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.socket.server.WebSocketService;

import com.marcs.annotations.interfaces.Client;
import com.marcs.app.notifications.client.domain.Notification;
import com.marcs.common.enums.WebRole;
import com.marcs.subscription.service.SubscriptionNotifierService;

/**
 * Client for {@link WebSocketService} to expose the given endpoint's to other
 * services.
 * 
 * @author Sam Butler
 * @since Dec 14, 2020
 */
@Client
public class SubscriptionNotifierClient {

    @Autowired
    private SubscriptionNotifierService service;

    /**
     * Push a web notification to a user for the given user id. The default socket
     * this notification will be sent to {@link #QUEUE_USER_NOTIFICATION}.
     * 
     * @param body    The body to be sent.
     * @param userIds The list of user ids of the user to send it too.
     */
    public void sendToUserIds(Notification body, Set<Integer> userIds) {
        service.sendToUserIds(body, userIds);
    }

    /**
     * Push a web notification to list of users with the given role. It will perform
     * a notification action with the passed in notification body. The default
     * socket this notification will be sent to {@link #QUEUE_USER_NOTIFICATION}.
     * 
     * @param body  The body to be sent.
     * @param roles The list of roles of the user to send it too.
     */
    public void sendToUserWebRoles(Notification body, Set<WebRole> roles) {
        service.sendToUserWebRoles(body, roles);
    }

    /**
     * Push a web notification to a user. The user id to send it to must be attached
     * on the receiver user id field.
     * 
     * @param body The body to be sent.
     */
    public void sendToUser(Notification body) {
        service.sendToUser(body);
    }

    /**
     * Push a web notification to a user for the given user id. The default socket
     * this notification will be sent to {@link #QUEUE_USER_NOTIFICATION}.
     * 
     * @param body   The body to be sent.
     * @param userId The user id of the user to send it too.
     */
    public void sendToUser(Notification body, int userId) {
        service.sendToUser(body, userId);
    }

    /**
     * Push a web notification to list of users with the given role. It will perform
     * a notification action with the passed in notification body. The default
     * socket this notification will be sent to {@link #QUEUE_USER_NOTIFICATION}.
     * 
     * @param body The body to be sent.
     * @param role The role of the user to send it too.
     */
    public void sendToUser(Notification body, WebRole role) {
        service.sendToUser(body, role);
    }

    /**
     * Send notification to anybody listening on the website subscription.
     * 
     * @param body The body to send.
     */
    public void sendToAll(Notification body) {
        service.sendToAll(body);
    }

    /**
     * Push a web notification to the general socket connnection. This is not a
     * specific user listener this would be sent to a topic subscription,
     * {@link #TOPIC_GENERAL_NOTIFICATION}.
     * 
     * @param body   The body to send.
     * @param socket The socket the notitication should go to.
     */
    public void send(Notification body, String socket) {
        service.send(body, socket);
    }

    /**
     * Push a web notification based on the given session id. Only the client with
     * the specified session id will receive the notification at
     * {@link #QUEUE_USER_NOTIFICATION}.
     * 
     * @param body      The body to be sent.
     * @param socket    The socket path the notification should be sent too.
     * @param sessionId The session of id of the client to send the notification
     *                  too.
     */
    public void send(Notification body, String socket, String sessionId) {
        service.send(body, socket, sessionId);
    }
}