/**
 * Copyright of Marcs App. All rights reserved.
 */
package com.marcs.exceptions.type;

/**
 * Exception thrown when query contains malicious characters..
 * 
 * @author Sam Butler
 * @since July 31, 2021
 */
public class MaliciousSqlQueryException extends BaseException {
    public MaliciousSqlQueryException() {
        super("Query contained malicious characters! Query can not contain ';', '-', or '\\' in string.");
    }
}
