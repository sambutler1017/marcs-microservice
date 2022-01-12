package com.marcs.app.notifications.client.domain;

import java.time.LocalDate;

import com.marcs.common.enums.NotificationType;

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

    private int linkId;

    private LocalDate insertDate;

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

    public int getLinkId() {
        return linkId;
    }

    public void setLinkId(int linkId) {
        this.linkId = linkId;
    }

    public LocalDate getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(LocalDate insertDate) {
        this.insertDate = insertDate;
    }
}
