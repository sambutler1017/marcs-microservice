package com.marcs.app.user.client.domain;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Class to create a user Schedule object
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Schema(description = "User Schedule object.")
public class UserSchedule {
    @Schema(description = "The schedule id the shift belongs too")
    private int scheduleId;

    @Schema(description = "The user id of the availability object")
    private int userId;

    @Schema(description = "The start date for a shift")
    private LocalDateTime startDate;

    @Schema(description = "The end date for a shift")
    private LocalDateTime endDate;

    @Schema(description = "When the user avaiablity was created")
    private LocalDateTime insertDate;

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public LocalDateTime getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(LocalDateTime insertDate) {
        this.insertDate = insertDate;
    }
}
