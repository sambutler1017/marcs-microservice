/**
 * Copyright of Marcs App. All rights reserved.
 */
package com.marcs.exceptions.type;

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