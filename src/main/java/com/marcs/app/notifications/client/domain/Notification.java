/**
 * Copyright of Marcs App. All rights reserved.
 */
package com.marcs.app.notifications.client.domain;

import java.time.LocalDateTime;

import com.marcs.common.enums.NotificationType;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Notification object that will map the data to the fields needed.
 * 
 * @author Sam Butler
 * @since December 21, 2021
 */
@Schema(description = "The notification data object.")
public class Notification {

    @Schema(description = "The unique id of the notification.")
    private int id;

    @Schema(description = "The notification type.")
    private NotificationType type;

    @Schema(description = "The read flag of the notification.")
    private boolean read;

    @Schema(description = "Who the notification is going too.")
    private int receiverId;

    @Schema(description = "Where the notification links too.")
    private int linkId;

    @Schema(description = "The socket path the notificaiton is going too.")
    private String destination;

    @Schema(description = "When the notification was created.")
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

    public int getLinkId() {
        return linkId;
    }

    public void setLinkId(int linkId) {
        this.linkId = linkId;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDateTime getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(LocalDateTime insertDate) {
        this.insertDate = insertDate;
    }
}
