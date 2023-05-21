package com.marcs.app.user.client.domain;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.validation.constraints.NotNull;

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

    @Schema(description = "The start time for a shift")
    @NotNull(message = "Invalid startTime: Start Time can not be null")
    private LocalTime startTime;

    @Schema(description = "The end time for a shift")
    @NotNull(message = "Invalid endTime: End Time can not be null")
    private LocalTime endTime;

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

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public LocalDateTime getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(LocalDateTime insertDate) {
        this.insertDate = insertDate;
    }
}
