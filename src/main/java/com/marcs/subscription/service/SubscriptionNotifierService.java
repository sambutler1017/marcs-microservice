package com.marcs.subscription.service;

import java.util.List;
import java.util.Optional;
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

    @Autowired
    private WebNotifierService webNotifierService;

    @Autowired
    private SimpUserRegistry userRegistry;

    /**
     * Push a web notification to a user for the given user id. The default socket
     * this notification will be sent to {@link #QUEUE_USER_NOTIFICATION}.
     * 
     * @param body   The body to be sent.
     * @param userId The user id of the user to send it too.
     */
    public void sendToUser(Notification body, int userId) {
        getActiveUserSessionByUserId(userId).ifPresent(u -> send(body, QUEUE_USER_NOTIFICATION, u.getName()));
    }

    /**
     * Push a web notification to list of users with the given role. It will perform
     * a notification action with the passed in notification body. The default
     * socket this notification will be sent to {@link #QUEUE_USER_NOTIFICATION}.
     * 
     * @param action The action to perform.
     * @param body   The body to be sent.
     * @param role   The role of the user to send it too.
     */
    public void sendToUser(Notification body, WebRole role) {
        List<UserPrincipal> sessionList = getActiveUserSessionByWebRole(role);
        if(!sessionList.isEmpty()) {
            for(UserPrincipal u : sessionList) {
                send(body, QUEUE_USER_NOTIFICATION, u.getName());
            }
        }
    }

    /**
     * Push a web notification to a user for the given user id. The default socket
     * this notification will be sent to {@link #QUEUE_USER_NOTIFICATION}.
     * 
     * @param body    The body to be sent.
     * @param userIds The list of user ids of the user to send it too.
     */
    public void sendToUserIds(Notification body, Set<Integer> userIds) {
        for(Integer id : userIds) {
            sendToUser(body, id);
        }
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
        for(WebRole r : roles) {
            sendToUser(body, r);
        }
    }

    /**
     * Push a web notification based on the given session id. Only the client with
     * the specified session id will receive the notification.
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
     * @return Optional of the user Principal.
     */
    private Optional<UserPrincipal> getActiveUserSessionByUserId(int userId) {
        return getActiveUserSessions().stream().filter(u -> u.getUser().getId() == userId).findFirst();
    }

    /**
     * Get the subscription session of users by role
     * 
     * @param role The user role to find.
     * @return List of the user Principal.
     */
    private List<UserPrincipal> getActiveUserSessionByWebRole(WebRole role) {
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
