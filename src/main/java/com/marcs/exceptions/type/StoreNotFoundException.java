/**
 * Copyright of Marcs App. All rights reserved.
 */
package com.marcs.exceptions.type;

/**
 * Exception thrown when store can not be found.
 * 
 * @author Sam Butler
 * @since July 31, 2021
 */
public class StoreNotFoundException extends BaseException {
    public StoreNotFoundException(String message) {
        super(message);
    }
}