/**
 * Copyright (c) 2023 Marcs App.
 * All rights reserved.
 */
package com.marcs.common.exceptions;

/**
 * Base Exception class. Will omit stack trace and only display exception.
 * 
 * @author Sam Butler
 * @since July 31, 2021
 */
public class BaseException extends RuntimeException {

    public BaseException(String message) {
        super(message);
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
