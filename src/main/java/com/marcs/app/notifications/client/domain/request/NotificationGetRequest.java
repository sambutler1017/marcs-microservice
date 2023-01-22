package com.marcs.app.notifications.client.domain.request;

import java.util.Set;

import com.marcs.common.enums.NotificationType;
import com.marcs.common.page.domain.PageParam;

/**
 * Notification get request for filtering out request.
 * 
 * @author Sam Butler
 * @since December 21, 2021
 */
public class NotificationGetRequest implements PageParam {

    private Set<Integer> id;

    private Set<NotificationType> type;

    private Set<Integer> receiverId;

    private Boolean read;

    private int pageSize;

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
