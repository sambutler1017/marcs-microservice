package com.marcs.common.enums;

/**
 * Enums for all the possible user roles.
 * 
 * @author Sam Butler
 * @since September 6, 2021
 */
public enum WebRole {
	EMPLOYEE(1), CORPORATE_USER(2), CUSTOMER_SERVICE_MANAGER(3), ASSISTANT_MANAGER(4), MANAGER(5), DISTRICT_MANAGER(6),
	REGIONAL(7), SITE_ADMIN(8), ADMIN(9);

	private int id;

	WebRole(int type) {
		this.id = type;
	}

	public int id() {
		return id;
	}

	public static WebRole getRole(int id) {
		for (WebRole w : WebRole.values())
			if (w.id == id)
				return w;
		return EMPLOYEE;
	}

	public boolean isManager() {
		return id > 2 && id < 6;
	}

	public int getValue() {
		return id;
	}
}
