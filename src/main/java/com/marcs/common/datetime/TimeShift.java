package com.marcs.common.datetime;

import java.time.LocalTime;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Class to create a Time shift object
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Schema(description = "Time Shift object.")
public class TimeShift {

    @Schema(description = "The start time for a shift")
    private LocalTime startTime;

    @Schema(description = "The end time for a shift")
    private LocalTime endTime;

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
}
