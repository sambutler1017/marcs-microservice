package com.globals.enums;

public enum WebRole {
	USER("user"), ADMIN("admin"), SITEADMIN("siteadmin"), REGIONAL("regional"), MANAGER("manager");

	private String text;

	WebRole(String type) {
		this.text = type;
	}

	public String text() {
		return text;
	}
}
