/**
 * 
 */
package com.globals.exceptions;

/**
 * @author sambu
 *
 */
public class ErrorInSqlQueryException extends Exception {

	private static final long serialVersionUID = 1L;

	public ErrorInSqlQueryException(String errorMessage) {
		super(errorMessage);
	}
}
