package com.marcs.common.enums;

/**
 * Enums for a sqlTag for building and searching for sql fragments.
 * 
 * @author Sam Butler
 * @since September 6, 2021
 */
public enum SqlTag {
	NAME("@NAME"), INCLUDE("@INCLUDE"), VALUE_ID(":");

	private String annotation;

	SqlTag(String annotation) {
		this.annotation = annotation;
	}

	public String text() {
		return annotation;
	}

	public boolean in(String str) {
		return str.contains(annotation);
	}
}
