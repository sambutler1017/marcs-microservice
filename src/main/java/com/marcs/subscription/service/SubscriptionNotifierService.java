/**
 * Copyright of Marcs App. All rights reserved.
 */
package com.marcs.subscription.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Component;

import com.marcs.app.notifications.client.domain.Notification;
import com.marcs.common.enums.WebRole;
import com.marcs.subscription.client.domain.UserPrincipal;

/**
 * Subscription service for managing and processing notifications.
 * 
 * @author Sam Butler
 * @since July 27, 2022
 */
@Component
public class SubscriptionNotifierService {
    private static final String QUEUE_USER_NOTIFICATION = "/queue/user/notification";
    private static final String TOPIC_GENERAL_NOTIFICATION = "/topic/general/notification";

    @Autowired
    private WebNotifierService webNotifierService;

    @Autowired
    private SimpUserRegistry userRegistry;

    /**
     * Push a web notification to a user for the given user id. The default socket
     * this notification will be sent to {@link #QUEUE_USER_NOTIFICATION}.
     * 
     * @param body    The body to be sent.
     * @param userIds The list of user ids of the user to send it too.
     */
    public void sendToUserIds(Notification body, Set<Integer> userIds) {
        userIds.forEach(id -> sendToUser(body, id));
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
        roles.forEach(r -> sendToUser(body, r));
    }

    /**
     * Push a web notification to a user. The user id to send it to must be attached
     * on the receiver user id field.
     * 
     * @param body The body to be sent.
     */
    public void sendToUser(Notification body) {
        sendToUser(body, body.getReceiverId());
    }

    /**
     * Push a web notification to a user for the given user id. The default socket
     * this notification will be sent to {@link #QUEUE_USER_NOTIFICATION}.
     * 
     * @param body   The body to be sent.
     * @param userId The user id of the user to send it too.
     */
    public void sendToUser(Notification body, int userId) {
        getActiveUserSessionsByUserId(userId).forEach(u -> send(body, QUEUE_USER_NOTIFICATION, u.getName()));
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
        getActiveUserSessionsByWebRole(role).forEach(u -> send(body, QUEUE_USER_NOTIFICATION, u.getName()));
    }

    /**
     * Send notification to anybody listening on the website subscription.
     * 
     * @param body The body to send.
     */
    public void sendToAll(Notification body) {
        send(body, TOPIC_GENERAL_NOTIFICATION);
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
        webNotifierService.send(buildNotification(body, socket));
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
        webNotifierService.send(buildNotification(body, socket), sessionId);
    }

    /**
     * Will get the active users connected to the websocket session.
     * 
     * @return List of SimpUser connections.
     */
    public List<UserPrincipal> getActiveUserSessions() {
        return userRegistry.getUsers().stream().map(v -> (UserPrincipal) v.getPrincipal()).collect(Collectors.toList());
    }

    /**
     * Get the subscription session of a user by their id.
     * 
     * @param userId The id of the user to find.
     * @return List of the user Principal.
     */
    private List<UserPrincipal> getActiveUserSessionsByUserId(int userId) {
        return getActiveUserSessions().stream().filter(u -> u.getUser().getId() == userId).collect(Collectors.toList());
    }

    /**
     * Get the subscription session of users by role
     * 
     * @param role The user role to find.
     * @return List of the user Principal.
     */
    private List<UserPrincipal> getActiveUserSessionsByWebRole(WebRole role) {
        return getActiveUserSessions().stream().filter(u -> u.getUser().getWebRole().equals(role))
                .collect(Collectors.toList());
    }

    /**
     * Builds the {@link Notification} object.
     * 
     * @param n           The base notification
     * @param destination Where the notification is to be sent.
     * @return The new {@link Notification} body.
     */
    private Notification buildNotification(Notification n, String destination) {
        n.setDestination(destination);
        return n;
    }
}
