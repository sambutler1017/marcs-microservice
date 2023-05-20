package com.marcs.app.user.client.domain;

import java.time.LocalDateTime;
import java.util.List;

import com.marcs.common.datetime.DateShift;

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

    @Schema(description = "The list of shifts the user is scheduled for")
    private List<DateShift> shifts;

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

    public List<DateShift> getShifts() {
        return shifts;
    }

    public void setShifts(List<DateShift> shifts) {
        this.shifts = shifts;
    }

    public LocalDateTime getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(LocalDateTime insertDate) {
        this.insertDate = insertDate;
    }
}
