/**
 * Copyright (c) 2023 Marcs App.
 * All rights reserved.
 */
package com.marcs.common.abstracts;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.jdbc.core.RowMapper;

/**
 * Base mapper class for mappers
 * 
 * @author Sam Butler
 * @since April 21, 2022
 */
public abstract class AbstractMapper<T> extends AbstractSqlGlobals implements RowMapper<T> {

    /**
     * Formats a date time to the local date time object.
     * 
     * @param d The date to format.
     * @return The new LocalDateTime format.
     */
    public LocalDateTime parseDateTime(String d) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(d, formatter);
    }

    /**
     * Formats a date to the local date object.
     * 
     * @param d The date to format.
     * @return The new LocalDate format.
     */
    public LocalDate parseDate(String d) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDate.parse(d, formatter);
    }
}
