package com.globals.exceptions;

public class InvalidParamValue extends Exception {

	private static final long serialVersionUID = -2845627044591951665L;

	public InvalidParamValue(String message, String param, String file, String errorString, int line) {
		super(String.format("\n\n\t%s %s \n\tFile Location: %s.insitesql\n\tLine Number: %s\n\tError String: '%s'\n",
				message, param, file, line, errorString));
	}
}
