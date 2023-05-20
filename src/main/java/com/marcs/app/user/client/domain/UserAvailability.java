package com.marcs.app.user.client.domain;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.marcs.common.datetime.TimeShift;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Class to create a user availability object
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Schema(description = "User Availability object.")
public class UserAvailability {

    @Schema(description = "The unique id of the user availability")
    private int id;

    @Schema(description = "The user id of the availability object")
    private int userId;

    @Schema(description = "The day of the week the user is available")
    @NotNull(message = "Invalid weekDay: Week day is a required field")
    private DayOfWeek weekDay;

    @Schema(description = "The List user shift when they are available")
    @NotEmpty(message = "Invalid shifts: Can not be empty or null")
    private List<TimeShift> shifts;

    @Schema(description = "When the user avaiablity was created")
    private LocalDateTime insertDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public DayOfWeek getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(DayOfWeek weekDay) {
        this.weekDay = weekDay;
    }

    public List<TimeShift> getShifts() {
        return shifts;
    }

    public void setShifts(List<TimeShift> shifts) {
        this.shifts = shifts;
    }

    public LocalDateTime getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(LocalDateTime insertDate) {
        this.insertDate = insertDate;
    }
}
