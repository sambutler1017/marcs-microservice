package com.marcs.app.blockOutDate.client.domain;

import java.util.Date;

/**
 * Class to represent a block out date object
 * 
 * @author Sam Butler
 * @since May 11, 2021
 */
public class BlockOutDate {
    private int id;

    private Date startDate;

    private Date endDate;

    private int insertUserId;

    private Date insertDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getInsertUserId() {
        return insertUserId;
    }

    public void setInsertUserId(int insertUserId) {
        this.insertUserId = insertUserId;
    }

    public Date getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(Date insertDate) {
        this.insertDate = insertDate;
    }
}
