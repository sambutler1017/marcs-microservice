package com.marcs.common.datetime;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Class to create a date shift object
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Schema(description = "Date Shift object")
public class DateShift {

    @Schema(description = "The start date for a shift")
    private LocalDateTime startDate;

    @Schema(description = "The end date for a shift")
    private LocalDateTime endDate;

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
}
