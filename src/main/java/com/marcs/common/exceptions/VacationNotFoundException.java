/**
 * Copyright (c) 2023 Marcs App.
 * All rights reserved.
 */
package com.marcs.common.exceptions;

/**
 * Exception thrown when vacation can not be found.
 * 
 * @author Sam Butler
 * @since July 31, 2021
 */
public class VacationNotFoundException extends BaseException {
    public VacationNotFoundException(String message) {
        super(message);
    }
}