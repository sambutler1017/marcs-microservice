/**
 * Copyright of Marcs App. All rights reserved.
 */
package com.marcs.exceptions.type;

/**
 * Exception thrown when user can not be found or updated.
 * 
 * @author Sam Butler
 * @since July 31, 2021
 */
public class UserNotFoundException extends BaseException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
