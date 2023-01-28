/**
 * Copyright (c) 2023 Marcs App.
 * All rights reserved.
 */
package com.marcs.app.notifications.client.domain.request;

import java.util.Set;

import com.marcs.common.enums.NotificationType;
import com.marcs.common.page.domain.PageParam;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Notification get request for filtering out request.
 * 
 * @author Sam Butler
 * @since December 21, 2021
 */
@Schema(description = "Notification get request object.")
public class NotificationGetRequest implements PageParam {

    @Schema(description = "Set of notification ids.")
    private Set<Integer> id;

    @Schema(description = "The notification type.")
    private Set<NotificationType> type;

    @Schema(description = "Set of who the notification is for.")
    private Set<Integer> receiverId;

    @Schema(description = "The read flag of the notification.")
    private Boolean read;

    @Schema(defaultValue = "The page size of the return data.")
    private int pageSize;

    @Schema(description = "The row offset of the data.")
    private int rowOffset;

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

    public Set<Integer> getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Set<Integer> receiverId) {
        this.receiverId = receiverId;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getRowOffset() {
        return rowOffset;
    }

    public void setRowOffset(int rowOffset) {
        this.rowOffset = rowOffset;
    }
}
