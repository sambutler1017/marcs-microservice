package com.marcs.app.notifications.client.domain.request;

import java.util.Set;

import com.marcs.common.enums.NotificationType;

/**
 * Notification get request for filtering out request.
 * 
 * @author Sam Butler
 * @since December 21, 2021
 */
public class NotificationGetRequest {
    private Set<Integer> id;

    private Set<NotificationType> type;

    private Boolean read;

    private Set<Integer> receiverId;

    public Set<Integer> getId() {
        return id;
    }

    public void setId(Set<Integer> id) {
        this.id = id;
    }

    public Set<NotificationType> getType() {
        return type;
    }

    public void setType(Set<NotificationType> type) {
        this.type = type;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public Set<Integer> getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Set<Integer> receiverId) {
        this.receiverId = receiverId;
    }
}
