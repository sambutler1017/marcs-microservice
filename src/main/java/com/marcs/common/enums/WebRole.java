/**
 * Copyright of Marcs App. All rights reserved.
 */
package com.marcs.common.enums;

import java.util.List;

/**
 * Enums for all the possible user roles.
 * 
 * @author Sam Butler
 * @since September 6, 2021
 */
public enum WebRole implements TextEnum {
	PART_TIME_EMPLOYEE(1, "PART_TIME_EMPLOYEE"),
	EMPLOYEE(2, "EMPLOYEE"),
	CORPORATE_USER(3, "CORPORATE_USER"),
	CUSTOMER_SERVICE_MANAGER(4, "CUSTOMER_SERVICE_MANAGER"),
	ASSISTANT_MANAGER(5, "ASSISTANT_MANAGER"),
	STORE_MANAGER(6, "STORE_MANAGER"),
	DISTRICT_MANAGER(7, "DISTRICT_MANAGER"),
	REGIONAL_MANAGER(8, "REGIONAL_MANAGER"),
	SITE_ADMIN(9, "SITE_ADMIN"),
	ADMIN(10, "ADMIN");

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
	 * between 4 and 6.
	 * 
	 * @return {@link Boolean} if the webrole is a manager.
	 */
	public boolean isManager() {
		List<Integer> managerRanks = List.of(4, 5, 6);
		return managerRanks.contains(rank);
	}

	/**
	 * Determines if the current webrole is a regional or not. Regionals are ranks 6
	 * and 7
	 * 
	 * @return {@link Boolean} if the webrole is a regional.
	 */
	public boolean isRegionalManager() {
		List<Integer> regionalRanks = List.of(7, 8);
		return regionalRanks.contains(rank);
	}

	/**
	 * Determines if the current webrole is a all access user or not. These roles
	 * are admins and corporate users.
	 * 
	 * @return {@link Boolean} if the webrole is a all access user.
	 */
	public boolean isAllAccessUser() {
		List<Integer> allAccessRanks = List.of(3, 9, 10);
		return allAccessRanks.contains(rank);
	}
}
