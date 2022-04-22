package com.marcs.app.notifications.client.domain;

import java.time.LocalDateTime;
import java.util.Set;

import com.marcs.common.enums.NotificationType;
import com.marcs.common.enums.WebRole;

/**
 * Notification object that will map the data to the fields needed.
 * 
 * @author Sam Butler
 * @since December 21, 2021
 */
public class Notification {
    private int id;

    private NotificationType type;

    private boolean read;

    private int receiverId;

    private Set<WebRole> receiverRoles;

    private int linkId;

    private LocalDateTime insertDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public Set<WebRole> getReceiverRoles() {
        return receiverRoles;
    }

    public void setReceiverRoles(Set<WebRole> receiverRoles) {
        this.receiverRoles = receiverRoles;
    }

    public int getLinkId() {
        return linkId;
    }

    public void setLinkId(int linkId) {
        this.linkId = linkId;
    }

    public LocalDateTime getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(LocalDateTime insertDate) {
        this.insertDate = insertDate;
    }
}
