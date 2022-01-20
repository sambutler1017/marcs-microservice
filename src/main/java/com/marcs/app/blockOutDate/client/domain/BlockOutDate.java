package com.marcs.app.blockOutDate.client.domain;

import java.time.LocalDate;

/**
 * Class to represent a block out date object
 * 
 * @author Sam Butler
 * @since May 11, 2021
 */
public class BlockOutDate {
    private int id;

    private LocalDate startDate;

    private LocalDate endDate;

    private int insertUserId;

    private LocalDate insertDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getInsertUserId() {
        return insertUserId;
    }

    public void setInsertUserId(int insertUserId) {
        this.insertUserId = insertUserId;
    }

    public LocalDate getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(LocalDate insertDate) {
        this.insertDate = insertDate;
    }
}
