/**
 * Copyright (c) 2023 Marcs App.
 * All rights reserved.
 */
package com.marcs.common.exceptions;

/**
 * Exception thrown when exception occurs for jwt.
 * 
 * @author Sam Butler
 * @since July 31, 2021
 */
public class JwtTokenException extends BaseException {
    public JwtTokenException(String msg) {
        super(msg);
    }
}