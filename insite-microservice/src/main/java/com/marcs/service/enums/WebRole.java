package com.marcs.service.enums;

public enum WebRole {
	USER(1), ADMIN(2), SITEADMIN(3), REGIONAL(4), MANAGER(5);

	private int id;

	WebRole(int type) {
		this.id = type;
	}

	public int id() {
		return id;
	}

	public static WebRole getRole(int id) {
        for(WebRole w : WebRole.values()) if(w.id == id) return w;
        return USER;
    }
}
