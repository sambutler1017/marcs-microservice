package com.marcs.common.exceptions.domain;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.marcs.common.util.TimeZoneUtil;

/**
 * Custom exception object to be returned when endpoints have errors.
 * 
 * @author Sam Butler
 * @since August 24, 2021
 */
public class ExceptionError {
    private LocalDateTime timestamp;

    private int status;

    private String error;

    private String message;

    public ExceptionError() {}

    public ExceptionError(String message) {
        this.status = HttpStatus.BAD_REQUEST.value();
        this.error = HttpStatus.BAD_REQUEST.getReasonPhrase();
        this.timestamp = LocalDateTime.now(TimeZoneUtil.defaultZone());
        this.message = message;
    }

    public ExceptionError(String message, HttpStatus status) {
        this.status = status.value();
        this.error = status.getReasonPhrase();
        this.timestamp = LocalDateTime.now(TimeZoneUtil.defaultZone());
        this.message = message;
    }

    public ExceptionError(LocalDateTime timestamp, HttpStatus status, String error, String message) {
        this.timestamp = timestamp;
        this.status = status.value();
        this.error = error;
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status.value();
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
