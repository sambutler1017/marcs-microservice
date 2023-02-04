/**
 * Copyright of Marcs App. All rights reserved.
 */
package com.marcs.common.enums;

/**
 * Enums for all the possible user roles.
 * 
 * @author Sam Butler
 * @since September 6, 2021
 */
public enum WebRole implements TextEnum {
	EMPLOYEE(1, "EMPLOYEE"),
	CORPORATE_USER(2, "CORPORATE_USER"),
	CUSTOMER_SERVICE_MANAGER(3, "CUSTOMER_SERVICE_MANAGER"),
	ASSISTANT_MANAGER(4, "ASSISTANT_MANAGER"),
	STORE_MANAGER(5, "STORE_MANAGER"),
	DISTRICT_MANAGER(6, "DISTRICT_MANAGER"),
	REGIONAL(7, "REGIONAL"),
	SITE_ADMIN(8, "SITE_ADMIN"),
	ADMIN(9, "ADMIN");

	private int rank;
	private String textId;

	WebRole(int rank, String textId) {
		this.rank = rank;
		this.textId = textId;
	}

	/**
	 * This is the rank the webrole holds.
	 * 
	 * @return {@link Integer} of the webroles rank
	 */
	public int getRank() {
		return rank;
	}

	/**
	 * String version of the enum value for the webrole.
	 * 
	 * @return {@link String} of the text id of the webrole enum.
	 */
	public String getTextId() {
		return textId;
	}

	/**
	 * This will get the {@link WebRole} for the given rank. If the rank value does
	 * not exist then it will return {@link WebRole#EMPLOYEE} rank.
	 * 
	 * @param rank The rank to search for in the enums
	 * @return {@link WebRole} of the rank found, if any.
	 */
	public static WebRole getRole(int rank) {
		for(WebRole w : WebRole.values())
			if(w.rank == rank) return w;
		return EMPLOYEE;
	}

	/**
	 * Determines if the current webrole is a manager or not. Managers are ranks
	 * between 2 and 6.
	 * 
	 * @return {@link Boolean} if the webrole is a manager.
	 */
	public boolean isManager() {
		return rank > 2 && rank < 6;
	}
}
