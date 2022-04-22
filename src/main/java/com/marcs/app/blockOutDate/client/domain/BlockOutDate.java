package com.marcs.app.blockOutDate.client.domain;

import java.time.LocalDateTime;

/**
 * Class to represent a block out date object
 * 
 * @author Sam Butler
 * @since May 11, 2021
 */
public class BlockOutDate {
    private int id;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private int insertUserId;

    private LocalDateTime insertDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public int getInsertUserId() {
        return insertUserId;
    }

    public void setInsertUserId(int insertUserId) {
        this.insertUserId = insertUserId;
    }

    public LocalDateTime getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(LocalDateTime insertDate) {
        this.insertDate = insertDate;
    }
}
